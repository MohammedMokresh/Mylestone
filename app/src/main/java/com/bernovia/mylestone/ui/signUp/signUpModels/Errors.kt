package com.bernovia.mylestone.ui.signUp.signUpModels

data class Errors(
    val email: List<String>,
    val full_messages: List<String>,
    val username: List<String>
)