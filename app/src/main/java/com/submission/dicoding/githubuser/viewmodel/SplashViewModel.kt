package com.submission.dicoding.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicoding.githubuser.data.UserRepository

class SplashViewModel(application: Application) : ViewModel() {
    private val repository = UserRepository(application)

    fun getThemeSetting() = repository.getTheme().asLiveData()
}