package com.submission.dicoding.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.databinding.ActivityThemeBinding
import com.submission.dicoding.githubuser.viewmodel.ThemeViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory

class ThemeActivity : AppCompatActivity() {
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var themeBinding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeBinding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(themeBinding.root)

        themeViewModel = obtainViewModel(this as AppCompatActivity)
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.title_menu_theme)
        actionbar.setDisplayHomeAsUpEnabled(true)
        darkMode()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): ThemeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ThemeViewModel::class.java]
    }

    private fun darkMode() {
        themeBinding.switchTheme.apply {
            themeViewModel.getThemeSettings().observe(this@ThemeActivity) { isDark ->
                val mode =
                    if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(mode)
                isChecked = isDark
            }
            setOnCheckedChangeListener { _, isChecked ->
                themeViewModel.saveThemeSettings(isChecked)
            }
        }
    }
}