package com.submission.dicoding.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.submission.dicoding.githubuser.data.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getFavoritedUser() = repository.getFavorite()
}