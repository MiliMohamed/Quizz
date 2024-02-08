package com.example.quizz

import android.app.Application
import com.example.quizz.di.modules.quizApiModule
import com.example.quizz.di.modules.roomDbModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(roomDbModules,quizApiModule)
        }
    }
}