package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetalleUi(
    val post: Post,
    val comentarios: List<Comentario>
)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _rawLista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    val lista: StateFlow<UiState<List<Post>>> = combine(_rawLista, _textoBusqueda) { state, query ->
        if (state is UiState.Exito) {
            if (query.isBlank()) {
                state
            } else {
                val filtrados = state.datos.filter { post ->
                    post.title.contains(query.trim(), ignoreCase = true)
                }
                UiState.Exito(filtrados)
            }
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Cargando
    )

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    private val _creado = MutableStateFlow<UiState<Post>?>(null)
    val creado: StateFlow<UiState<Post>?> = _creado.asStateFlow()

    private val _borrado = MutableStateFlow<UiState<Unit>?>(null)
    val borrado: StateFlow<UiState<Unit>?> = _borrado.asStateFlow()

    init {
        cargar()
    }

    fun buscar(texto: String) {
        _textoBusqueda.value = texto
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _rawLista.value = UiState.Cargando
            val filtro = _usuarioFiltro.value
            val resultado = if (filtro == null) {
                repo.listar()
            } else {
                repo.porUsuario(filtro)
            }
            resultado
                .onSuccess { _rawLista.value = UiState.Exito(it) }
                .onFailure { _rawLista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            try {
                coroutineScope {
                    val postDeferred = async { repo.detalle(id) }
                    val comentariosDeferred = async { repo.comentarios(id) }

                    val resPost = postDeferred.await()
                    val resComentarios = comentariosDeferred.await()

                    if (resPost.isSuccess && resComentarios.isSuccess) {
                        val post = resPost.getOrThrow()
                        val comentarios = resComentarios.getOrThrow()
                        _detalle.value = UiState.Exito(DetalleUi(post, comentarios))
                    } else {
                        val error = resPost.exceptionOrNull()
                            ?: resComentarios.exceptionOrNull()
                            ?: Exception("Error al cargar detalle")
                        _detalle.value = UiState.Error(mensajeDe(error))
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _detalle.value = UiState.Error(mensajeDe(e))
            }
        }
    }

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _creado.value = UiState.Cargando
            val nuevo = NuevoPost(userId = 1, title = titulo, body = texto)
            repo.crear(nuevo)
                .onSuccess { _creado.value = UiState.Exito(it) }
                .onFailure { _creado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarCreado() {
        _creado.value = null
    }

    fun borrar(id: Int) {
        viewModelScope.launch {
            _borrado.value = UiState.Cargando
            repo.borrar(id)
                .onSuccess { _borrado.value = UiState.Exito(Unit) }
                .onFailure { _borrado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarBorrado() {
        _borrado.value = null
    }
}
