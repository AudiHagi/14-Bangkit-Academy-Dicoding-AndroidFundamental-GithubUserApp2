package com.submission.dicoding.githubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.adapter.SectionsPagerAdapter
import com.submission.dicoding.githubuser.data.remote.response.GithubUser
import com.submission.dicoding.githubuser.databinding.ActivityDetailBinding
import com.submission.dicoding.githubuser.viewmodel.DetailViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var isChecked = false
    private lateinit var username: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        showLoading(false)
        setViewPager()
        val actionbar = supportActionBar
        actionbar!!.title = null
        actionbar.setDisplayHomeAsUpEnabled(true)
        detailViewModel = obtainViewModel(this as AppCompatActivity)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        type = intent.getStringExtra(EXTRA_TYPE) as String
        username = intent.getStringExtra(EXTRA_USERNAME) as String
        detailViewModel.getDetailUser(username).observe(this) { detailUser ->
            populateContentDetail(detailUser)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkExistUser(id)
            withContext(Dispatchers.Main) {
                isChecked = if (count > 0) {
                    isFavorited(true)
                    true
                } else {
                    isFavorited(false)
                    false
                }
            }
        }
        detailBinding.fbFavorite.setOnClickListener {
            isChecked = !isChecked
            val message = if (isChecked) "Add ${username} To" else "Remove ${username} From"
            detailViewModel.run {
                if (isChecked) addToFavorite(id, type, username, avatarUrl!!)
                else removeFromFavorite(id)
            }
            Toast.makeText(this, "$message Favorite", Toast.LENGTH_SHORT).show()
            isFavorited(isChecked)
        }
        detailBinding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Let's see ${username} Github Page")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    fun getData(): String = username

    private fun isFavorited(state: Boolean) {
        detailBinding.fbFavorite.setImageResource(
            if (state) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun populateContentDetail(detailUser: GithubUser) {
        detailBinding.apply {
            tvUsernameDetail.text = detailUser.login
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .circleCrop()
                .into(ivProfile)
            tvFollowerCount.text = detailUser.followers_count.toString()
            tvFollowingCount.text = detailUser.following_count.toString()
            tvNamaDetail.text = detailUser.nama
            supportActionBar?.apply {
                title = StringBuilder(detailUser.login).toString()
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun setViewPager() {
        detailBinding.viewPager.adapter = SectionsPagerAdapter(
            listOf(FollowerFragment(), FollowingFragment()), supportFragmentManager, lifecycle
        )
        TabLayoutMediator(detailBinding.tabs, detailBinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        val visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        detailBinding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnShare.visibility = visibility
            fbFavorite.visibility = visibility
            tvFollowerTitle.visibility = visibility
            tvFollowingTitle.visibility = visibility
            tabs.visibility = visibility
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}