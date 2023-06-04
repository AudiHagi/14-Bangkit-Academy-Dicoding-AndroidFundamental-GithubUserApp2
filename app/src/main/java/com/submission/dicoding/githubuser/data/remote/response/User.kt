package com.submission.dicoding.githubuser.data.remote.response

data class User(
    val id: Int,
    val type: String,
    val login: String,
    val avatar_url: String
)
