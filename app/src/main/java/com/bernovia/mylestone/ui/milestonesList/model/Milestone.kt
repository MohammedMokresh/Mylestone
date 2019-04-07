package com.bernovia.mylestone.ui.milestonesList.model

data class Milestone(
    val date: String,
    val id: Int,
    val story: String,
    val title: String,
    val user_name: String,
    val user_id: Int
)