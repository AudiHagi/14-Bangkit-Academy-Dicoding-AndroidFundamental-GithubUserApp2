package com.submission.dicoding.githubuser.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.dicoding.githubuser.data.local.entity.UserEntity
import com.submission.dicoding.githubuser.data.local.room.UserDao
import com.submission.dicoding.githubuser.data.local.room.UserDatabase
import com.submission.dicoding.githubuser.data.remote.response.GithubResponse
import com.submission.dicoding.githubuser.data.remote.response.GithubUser
import com.submission.dicoding.githubuser.data.remote.response.User
import com.submission.dicoding.githubuser.data.remote.retrofit.ApiConfig
import com.submission.dicoding.githubuser.data.remote.retrofit.ApiService
import com.submission.dicoding.githubuser.setting.SettingPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(application: Application) {
    private val pref: SettingPreferences
    private val userDao: UserDao
    private val retrofit: ApiService = ApiConfig.getApiService()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    init {
        val db: UserDatabase = UserDatabase.getInstance(application)
        userDao = db.userDao()
        pref = SettingPreferences.getInstance(application.dataStore)
    }

    fun searchUser(query: String): LiveData<ArrayList<User>> {
        val listUser = MutableLiveData<ArrayList<User>>()
        retrofit.getSearchUser(query).enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }
                val message = "Response code: ${response.code()} " +
                        when (response.code()) {
                            401 -> ": Forbidden"
                            403 -> ": Bad Request"
                            404 -> ": Not Found"
                            else -> ""
                        }
                Log.d("onResponseSearch", message)
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d("onFailureSearch", t.message!!)
            }
        })
        return listUser
    }

    fun detailUser(username: String): LiveData<GithubUser> {
        val userDetail = MutableLiveData<GithubUser>()
        retrofit.getUserDetail(username).enqueue(object : Callback<GithubUser> {
            override fun onResponse(
                call: Call<GithubUser>,
                response: Response<GithubUser>
            ) {
                if (response.isSuccessful) {
                    userDetail.postValue(response.body())
                }
                val message = "Response code: ${response.code()} " +
                        when (response.code()) {
                            401 -> ": Forbidden"
                            403 -> ": Bad Request"
                            404 -> ": Not Found"
                            else -> ""
                        }
                Log.d("onResponseDetail", message)
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                Log.d("onFailureDetail", t.message!!)
            }
        })
        return userDetail
    }

    fun listFollowers(username: String): LiveData<ArrayList<User>> {
        val userFollower = MutableLiveData<ArrayList<User>>()
        retrofit.getUserFollower(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    userFollower.postValue(response.body())
                }
                val message = "Response code: ${response.code()} " +
                        when (response.code()) {
                            401 -> ": Forbidden"
                            403 -> ": Bad Request"
                            404 -> ": Not Found"
                            else -> ""
                        }
                Log.d("onResponseFollowers", message)
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("onFailureFollowers", t.message!!)
            }
        })
        return userFollower
    }

    fun listFollowing(username: String): LiveData<ArrayList<User>> {
        val userFollowing = MutableLiveData<ArrayList<User>>()
        retrofit.getUserFollowing(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    userFollowing.postValue(response.body())
                }
                val message = "Response code: ${response.code()} " +
                        when (response.code()) {
                            401 -> ": Forbidden"
                            403 -> ": Bad Request"
                            404 -> ": Not Found"
                            else -> ""
                        }
                Log.d("onResponseFollowing", message)
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("onFailureFollowing", t.message!!)
            }
        })
        return userFollowing
    }

    fun getFavorite(): LiveData<List<UserEntity>> = userDao.getFavoritedUser()

    suspend fun checkUser(id: Int) = userDao.checkUser(id)

    fun addFavorite(id: Int, type: String, username: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserEntity(
                id,
                type,
                username,
                avatarUrl
            )
            userDao.addToFavorite(user)
        }
    }

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteFavorite(id)
        }
    }

    suspend fun saveTheme(darkMode: Boolean) = pref.saveThemeSetting(darkMode)

    fun getTheme() = pref.getThemeSetting()
}