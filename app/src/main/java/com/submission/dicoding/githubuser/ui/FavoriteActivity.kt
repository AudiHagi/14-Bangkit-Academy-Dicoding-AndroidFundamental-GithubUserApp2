package com.submission.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.adapter.UserAdapter
import com.submission.dicoding.githubuser.data.local.entity.UserEntity
import com.submission.dicoding.githubuser.data.remote.response.User
import com.submission.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.submission.dicoding.githubuser.viewmodel.FavoriteViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        showLoading(true)
        showTv(false)
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.user_favorite)
        actionbar.setDisplayHomeAsUpEnabled(true)
        adapter = UserAdapter()
        favoriteBinding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = this@FavoriteActivity.adapter
        }
        viewModel = obtainViewModel(this as AppCompatActivity)
        viewModel.getFavoritedUser().observe(this) {
            val list = mapList(it)
            if (it != null && it.isNotEmpty()) {
                showLoading(false)
                showTv(false)
                adapter.setData(list)
            } else {
                showLoading(false)
                showTv(true)
                adapter.setData(list)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun mapList(listFavorite: List<UserEntity>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in listFavorite) {
            val userMapped = User(
                user.id,
                user.type,
                user.login,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun showLoading(state: Boolean) {
        favoriteBinding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showTv(state: Boolean) {
        favoriteBinding.tvSetFavorite.visibility = if (state) View.VISIBLE else View.GONE
    }
}