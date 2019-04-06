package com.bernovia.mylestone.ui.login.loginModels

data class LoginResponseBody(
    val errors: List<String>,
    val success: Boolean,
    val data: Data
)