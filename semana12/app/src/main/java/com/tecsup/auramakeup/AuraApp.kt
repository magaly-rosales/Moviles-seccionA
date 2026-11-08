package com.tecsup.auramakeup

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

class AuraApp : Application(), ImageLoaderFactory {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .crossfade(true)
            .build()
}