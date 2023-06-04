package com.submission.dicoding.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.submission.dicoding.githubuser.data.UserRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getDetailUser(username: String) = repository.detailUser(username)

    fun addToFavorite(id: Int, type: String, username: String, avatarUrl: String) =
        repository.addFavorite(id, type, username, avatarUrl)

    suspend fun checkExistUser(id: Int) = repository.checkUser(id)

    fun removeFromFavorite(id: Int) = repository.removeFavorite(id)
}