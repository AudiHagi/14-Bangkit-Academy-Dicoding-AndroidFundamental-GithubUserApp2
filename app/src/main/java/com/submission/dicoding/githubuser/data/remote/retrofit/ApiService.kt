package com.submission.dicoding.githubuser.data.remote.retrofit

import com.submission.dicoding.githubuser.data.remote.response.GithubResponse
import com.submission.dicoding.githubuser.data.remote.response.GithubUser
import com.submission.dicoding.githubuser.data.remote.response.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<GithubUser>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}