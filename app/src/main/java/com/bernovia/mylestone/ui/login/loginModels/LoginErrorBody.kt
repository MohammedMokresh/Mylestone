package com.bernovia.mylestone.ui.login.loginModels

data class LoginErrorBody(
    val errors: List<String>,
    val success: Boolean
)

