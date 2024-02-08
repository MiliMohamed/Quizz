package com.example.quizz.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizz.model.LoginResult

import com.example.quizz.repositories.UserRepository
import com.example.quizz.utils.PasswordUtils
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun validateLogin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val hashedPassword = PasswordUtils.hashPassword(password)
                val user = userRepository.validateUser(username, hashedPassword)
                if (user != null) {
                    _loginResult.value = LoginResult(success = true, user = user, message = "Login réussi")

                } else {
                    _loginResult.value = LoginResult(success = false, message = "Identifiants incorrects")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error validating login", e)
                _loginResult.value = LoginResult(success = false, message = "Erreur de connexion")
            }
        }
    }



}