package com.submission.dicoding.githubuser.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.dicoding.githubuser.viewmodel.DetailViewModel
import com.submission.dicoding.githubuser.viewmodel.FavoriteViewModel
import com.submission.dicoding.githubuser.viewmodel.FollowerViewModel
import com.submission.dicoding.githubuser.viewmodel.FollowingViewModel
import com.submission.dicoding.githubuser.viewmodel.MainViewModel
import com.submission.dicoding.githubuser.viewmodel.SplashViewModel
import com.submission.dicoding.githubuser.viewmodel.ThemeViewModel

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                application
            ) as T

            modelClass.isAssignableFrom(FollowerViewModel::class.java) -> FollowerViewModel(
                application
            ) as T

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> FollowingViewModel(
                application
            ) as T

            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> ThemeViewModel(application) as T

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application)
            }
    }
}