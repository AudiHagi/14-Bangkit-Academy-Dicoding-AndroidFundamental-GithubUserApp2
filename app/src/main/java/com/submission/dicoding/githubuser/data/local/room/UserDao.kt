package com.submission.dicoding.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.submission.dicoding.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavoritedUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(userEntity: UserEntity)

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id_user = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id_user = :id")
    suspend fun deleteFavorite(id: Int): Int
}