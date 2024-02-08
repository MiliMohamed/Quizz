package com.example.quizz.di.modules
import com.example.quizz.repositories.QuizzRepository
import com.example.quizz.repositories.UserRepository
import com.example.quizz.services.QuizzApiService
import com.example.quizz.viewmodel.LoginViewModel
import com.example.quizz.viewmodel.QuizzViewModel
import com.example.quizz.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ViewModels = module {
    viewModel { RegisterViewModel(get()) }
    single { UserRepository(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { QuizzViewModel(get()) }
    single { QuizzRepository(get(),get()) }


}

data class ApiConfig(
    val baseUrl: String
)