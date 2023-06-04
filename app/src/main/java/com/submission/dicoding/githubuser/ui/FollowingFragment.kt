package com.submission.dicoding.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.githubuser.adapter.UserAdapter
import com.submission.dicoding.githubuser.databinding.FragmentFollowingBinding
import com.submission.dicoding.githubuser.viewmodel.FollowingViewModel
import com.submission.dicoding.githubuser.viewmodelfactory.ViewModelFactory

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is null")
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var detailActivity: DetailActivity
    private lateinit var username: String

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        showTv(false)
        detailActivity = activity as DetailActivity
        username = detailActivity.getData()
        showLoading(true)
        val adapter = UserAdapter()
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
        followingViewModel = obtainViewModel(context as AppCompatActivity)
        followingViewModel.getListFollowing(username).observe(viewLifecycleOwner) { list ->
            list?.let {
                showLoading(false)
                showTv(list.isEmpty())
                adapter.setData(list)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showTv(state: Boolean) {
        binding.tvNoFollowing.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FollowingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FollowingViewModel::class.java]
    }
}