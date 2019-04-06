package com.bernovia.mylestone.ui.createMilestone.createMilestoneModel

data class CreateMilestoneRequestBody(
    val date: String,
    val status: String,
    val story: String,
    val title: String
)