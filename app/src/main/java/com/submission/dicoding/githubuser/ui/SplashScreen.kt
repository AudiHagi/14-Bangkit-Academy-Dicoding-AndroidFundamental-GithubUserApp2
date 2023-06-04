package com.submission.dicoding.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.alarm.AlarmReceiver
import com.submission.dicoding.githubuser.viewmodel.SplashViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : AppCompatActivity() {
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashViewModel = obtainViewModel(this as AppCompatActivity)
        lifecycleScope.launch {
            delay(splashTime)
            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        isDarkMode()
    }

    private fun obtainViewModel(activity: AppCompatActivity): SplashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SplashViewModel::class.java]
    }

    private fun isDarkMode() {
        splashViewModel.getThemeSetting().observe(this) { isDark ->
            val mode =
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    companion object {
        const val splashTime: Long = 2000
    }
}