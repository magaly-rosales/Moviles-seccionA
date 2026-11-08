package com.tecsup.auramakeup

import android.content.Context
import com.tecsup.auramakeup.data.remote.RetrofitClient
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.data.repository.ItemRepositoryImpl

class AppContainer(private val context: Context) {

    val itemRepository: ItemRepository by lazy {
        ItemRepositoryImpl(RetrofitClient.api)
    }
}