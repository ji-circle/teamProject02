package com.example.msggrid

data class User(
    val id: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String,
    val group: List<String>,
    val profileImage: ProfileImage,
    var isLike: Boolean,
    var isChecked: Boolean
)
