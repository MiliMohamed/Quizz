package com.example.quizz.di

import android.content.Context
import com.example.quizz.BuildConfig
import com.example.quizz.di.modules.ApiConfig
import com.example.quizz.di.modules.ViewModels
import com.example.quizz.di.modules.quizApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectModuleDependencies(context: Context) {
    try {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(modules)
    }
}

/*fun initializeApiModule() {
    val apiConfig  = ApiConfig(baseUrl = BuildConfig.BASE_URL)
    modules.add(module {
        single { apiConfig  }
    })
}*/


private val modules = mutableListOf(ViewModels, quizApiModule)
