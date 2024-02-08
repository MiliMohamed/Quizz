package com.example.quizz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.quizz.databinding.LoginBinding
import com.example.quizz.di.injectModuleDependencies
import com.example.quizz.model.LoginResult
import com.example.quizz.view.Quizz
import com.example.quizz.view.Registration
import com.example.quizz.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class Login : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectModuleDependencies(this@Login);


        intent.getStringExtra("USERNAME")?.let { username ->
            binding.username.setText(username)
        }

        setupLoginAction()
        setupRegisterLinkAction()
        loginViewModel.loginResult.observe(this) { result ->
            handleLoginResult(result)
        }

    }

    private fun setupLoginAction() {
        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            loginViewModel.validateLogin(username, password)
        }
    }
    private fun setupRegisterLinkAction() {
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
    private fun handleLoginResult(result: LoginResult) {
        if (result.success) {
            val intent = Intent(this, Quizz::class.java)
            val userId = result.user?.id
            Log.d("Login", "User ID: $userId")
            intent.putExtra("USER_ID", result.user?.id)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }
    }

}