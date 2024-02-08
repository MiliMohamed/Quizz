package com.example.quizz.utils

import java.security.MessageDigest
import java.util.Base64

object PasswordUtils {
    fun hashPassword(password: String): String {
        val passwordToHash = password + "esgi"
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(passwordToHash.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hash)
    }
}