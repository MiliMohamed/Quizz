package com.example.quizz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizz.model.User
import com.example.quizz.repositories.UserRepository
import com.example.quizz.utils.PasswordUtils
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository

) : ViewModel() {

    val validationMessage = MutableLiveData<String?>()
    val navigateToLogin = MutableLiveData<String?>()

    fun validateRegistration(username: String, email: String, password: String, confirmPassword: String) {

        viewModelScope.launch {
            if (userRepository.usernameExists(username)) {
                validationMessage.value = "This username is already taken. Please choose a different username."
                return@launch
            }
            if (userRepository.emailExists(email)) {
                validationMessage.value = "User with this email already exists. Please try again."
                return@launch
            }
            if (username.length <= 3) {
                validationMessage.value = "Pseudo must be longer than 3 characters"
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                validationMessage.value = "Email is not valid"
                return@launch
            }

            val passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$"
            if (!password.matches(passwordPattern.toRegex())) {
                validationMessage.value =
                    "Password needs: 1 digit, 1 lower, 1 upper, 1 special char"
                return@launch
            }

            if (password != confirmPassword) {
                validationMessage.value = "Password and confirmation password do not match"
                return@launch
            }

            registerUser(username, email, password)
        }
    }
    private fun registerUser(username: String, email: String, password: String) {

        val hashedPassword = PasswordUtils.hashPassword(password)
        val newUser = User(username = username, email = email, password = hashedPassword)

        viewModelScope.launch {
            userRepository.registerUser(newUser)
            validationMessage.postValue("User successfully registered")
            navigateToLogin.postValue(username)
        }
    }


}