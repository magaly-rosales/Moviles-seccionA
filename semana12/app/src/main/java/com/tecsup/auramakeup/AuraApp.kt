package com.tecsup.auramakeup

import android.app.Application

class AuraApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}