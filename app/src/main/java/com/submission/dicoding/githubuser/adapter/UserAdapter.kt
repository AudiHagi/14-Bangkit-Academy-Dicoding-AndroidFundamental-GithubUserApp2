package com.submission.dicoding.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.dicoding.githubuser.data.remote.response.User
import com.submission.dicoding.githubuser.databinding.ItemUserBinding
import com.submission.dicoding.githubuser.ui.DetailActivity
import com.submission.dicoding.githubuser.utils.DiffUtils
import java.util.Locale

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var oldListUser = emptyList<User>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = oldListUser[position]
        holder.binding(user)
    }

    override fun getItemCount(): Int = oldListUser.size

    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user: User) {
            binding.apply {
                tvUsername.text = user.login
                tvType.text = user.type.lowercase(Locale.getDefault())
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .circleCrop()
                    .into(binding.ivProfile)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, user.id)
                        putExtra(DetailActivity.EXTRA_TYPE, user.type)
                        putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                        putExtra(DetailActivity.EXTRA_AVATAR, user.avatar_url)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    fun setData(newListUser: ArrayList<User>) {
        val diffUtil = DiffUtils(oldListUser, newListUser)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldListUser = newListUser
        diffResults.dispatchUpdatesTo(this)
    }
}