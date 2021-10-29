package com.veronica.idn.githubapp.presentation.detail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.databinding.FragmentFollowingBinding
import com.veronica.idn.githubapp.presentation.home.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private lateinit var followingBinding: FragmentFollowingBinding
    private lateinit var followingViewModel: FollowingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followingBinding = FragmentFollowingBinding.inflate(layoutInflater)
        setRecyclerView()
        setViewModelProvider()
        observeData()
        loading()
        setError()
        return followingBinding.root
    }

    private fun setError() {
        followingBinding
    }

    private fun loading() {
        followingViewModel.loading.observe(viewLifecycleOwner, {isLoading ->
            if (isLoading ) {
                followingBinding.pbFollowers.visibility = View.VISIBLE
                followingBinding.rvFollowing.visibility = View.GONE
            } else {
                followingBinding.pbFollowers.visibility = View.GONE
                followingBinding.rvFollowing.visibility = View.VISIBLE
            }
        })
    }

    private fun observeData() {
        val username = arguments?.getString(USERNAME)
        followingViewModel.getFollowing(username ?: "")
        followingViewModel.followingLiveData.observe(viewLifecycleOwner, { listUser ->
            if ((listUser?.size ?: 0) == 0) {
                followingBinding.llFollowing.visibility = View.VISIBLE
                followingBinding.rvFollowing.visibility = View.GONE
            } else {
                followingBinding.llFollowing.visibility = View.GONE
                followingBinding.rvFollowing.visibility = View.VISIBLE
                val userAdapter = UserAdapter(listUser)
                followingBinding.rvFollowing.adapter = userAdapter
            }
        })
    }

    private fun setViewModelProvider() {
        followingViewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
    }

    private fun setRecyclerView() {
        followingBinding.rvFollowing.setHasFixedSize(true)
        followingBinding.rvFollowing.layoutManager = LinearLayoutManager(context)
        followingBinding.rvFollowing.adapter = UserAdapter(listOf())
    }

    companion object {
        private const val USERNAME = "username"
        fun newInstance(username: String): Fragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }
}