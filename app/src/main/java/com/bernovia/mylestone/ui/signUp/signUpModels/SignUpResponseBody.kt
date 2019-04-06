package com.bernovia.mylestone.ui.signUp.signUpModels

data class SignUpResponseBody(
    val data: Data,
    val errors: Errors,
    val status: String
)