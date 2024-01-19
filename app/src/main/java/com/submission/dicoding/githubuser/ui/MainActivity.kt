package com.submission.dicoding.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.adapter.UserAdapter
import com.submission.dicoding.githubuser.databinding.ActivityMainBinding
import com.submission.dicoding.githubuser.viewmodel.MainViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.FailedTV.visibility = View.GONE
        mainViewModel = obtainViewModel(this as AppCompatActivity)
        adapter = UserAdapter()
        mainBinding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
        val randomString = getRandomString(length)
        searchUser(randomString)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.menu_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun searchUser(query: String) {
        showLoading(true)
        mainViewModel.getSearchUser(query).observe(this) { list ->
            list.let {
                showLoading(false)
                if (it.isNotEmpty()) {
                    mainBinding.FailedTV.visibility = View.GONE
                    mainBinding.rvListUser.visibility = View.VISIBLE
                    adapter.setData(it)
                } else {
                    mainBinding.FailedTV.visibility = View.VISIBLE
                    mainBinding.rvListUser.visibility = View.GONE
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun showLoading(state: Boolean) {
        mainBinding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_theme -> {
                val moveToTheme = Intent(this@MainActivity, ThemeActivity::class.java)
                startActivity(moveToTheme)
            }

            R.id.menu_favorite -> {
                val moveToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveToFavorite)
            }

            R.id.menu_notif -> {
                val moveToNotif = Intent(this@MainActivity, RepeatingNotifActivity::class.java)
                startActivity(moveToNotif)
            }

            R.id.menu_about -> {
                val moveToAbout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveToAbout)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val length = 1
    }
}