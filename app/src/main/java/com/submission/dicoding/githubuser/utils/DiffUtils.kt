package com.submission.dicoding.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.submission.dicoding.githubuser.data.remote.response.User

class DiffUtils(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            oldList[oldItemPosition].type != newList[newItemPosition].type -> {
                false
            }

            oldList[oldItemPosition].login != newList[newItemPosition].login -> {
                false
            }

            oldList[oldItemPosition].avatar_url != newList[newItemPosition].avatar_url -> {
                false
            }

            else -> true
        }
    }
}