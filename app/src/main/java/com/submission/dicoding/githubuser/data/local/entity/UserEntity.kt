package com.submission.dicoding.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_user")
    val id: Int,

    @ColumnInfo(name = "type_user")
    val type: String,

    @ColumnInfo(name = "username")
    val login: String,

    @ColumnInfo(name = "profile")
    val avatar_url: String
)