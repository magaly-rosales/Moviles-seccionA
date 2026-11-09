package com.tecsup.auramakeup.data.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.auramakeup.domain.model.Rol
import com.tecsup.auramakeup.domain.model.Sesion
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : AuthRepository {

    override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> = try {
        val uid = auth.signInWithEmailAndPassword(correo, clave).await().user!!.uid
        Result.success(leerSesion(uid, correo))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(Exception("Correo o contraseña incorrectos"))
    } catch (e: FirebaseAuthInvalidUserException) {
        Result.failure(Exception("Correo o contraseña incorrectos"))
    } catch (e: FirebaseNetworkException) {
        Result.failure(Exception("No hay conexión a internet"))
    } catch (e: Exception) {
        Result.failure(Exception("No se pudo iniciar sesión"))
    }

    override suspend fun registrar(nombre: String, correo: String, clave: String): Result<Sesion> = try {
        val resultado = auth.createUserWithEmailAndPassword(correo, clave).await()
        val uid = resultado.user!!.uid
        val datos = mapOf(
            "rol" to "usuario",
            "nombre" to nombre
        )
        db.collection("usuarios").document(uid).set(datos).await()
        Result.success(Sesion(uid, correo, Rol.USUARIO, nombre))
    } catch (e: FirebaseAuthUserCollisionException) {
        Result.failure(Exception("El correo ya se encuentra registrado"))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(Exception("El correo no tiene un formato válido"))
    } catch (e: FirebaseAuthWeakPasswordException) {
        Result.failure(Exception("La contraseña debe tener al menos 6 caracteres"))
    } catch (e: FirebaseNetworkException) {
        Result.failure(Exception("No hay conexión a internet"))
    } catch (e: Exception) {
        Result.failure(Exception(e.message ?: "No se pudo crear la cuenta"))
    }

    override suspend fun sesionActual(): Sesion? {
        val u = auth.currentUser ?: return null
        return try {
            leerSesion(u.uid, u.email ?: "")
        } catch (e: Exception) {
            Sesion(u.uid, u.email ?: "", Rol.USUARIO, "")
        }
    }

    override fun cerrarSesion() = auth.signOut()

    private suspend fun leerSesion(uid: String, correo: String): Sesion {
        val doc = db.collection("usuarios").document(uid).get().await()
        val rol = if (doc.getString("rol") == "admin") Rol.ADMIN else Rol.USUARIO
        val nombre = doc.getString("nombre") ?: ""
        return Sesion(uid, correo, rol, nombre)
    }
}
