package com.veronica.idn.githubapp.presentation.detail.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.databinding.FragmentFollowerBinding
import com.veronica.idn.githubapp.presentation.home.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment() {
    private lateinit var followerBinding: FragmentFollowerBinding
    private lateinit var followersViewModel: FollowersViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followerBinding = FragmentFollowerBinding.inflate(layoutInflater)
        showFollower()
        setViewModelProvider()
        observeData()
        loading()
        setError()
        return followerBinding.root
    }

    private fun setError() {
        followersViewModel.error.observe(viewLifecycleOwner, {
            if (it == null) {
                followerBinding.llFollowerError.visibility = View.GONE
                followerBinding.rvFollower.visibility = View.VISIBLE
            } else {
                followerBinding.llFollowerError.visibility = View.VISIBLE
                followerBinding.rvFollower.visibility = View.GONE

            }
        })
    }

    private fun loading() {
        followersViewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                followerBinding.pbFollowers.visibility = View.VISIBLE
                followerBinding.rvFollower.visibility = View.GONE
            } else {
                followerBinding.pbFollowers.visibility = View.GONE
                followerBinding.rvFollower.visibility = View.VISIBLE

            }
        })

    }

    private fun observeData() {
        val username = arguments?.getString(USERNAME)
        followersViewModel.getFollowers(username ?: "")
        followersViewModel.followerLiveData.observe(viewLifecycleOwner, { it ->
            if ((it?.size ?: 0) == 0) {
                followerBinding.llFollowerNotFound.visibility = View.VISIBLE
                followerBinding.rvFollower.visibility = View.GONE
            } else {
                followerBinding.llFollowerNotFound.visibility = View.GONE
                followerBinding.rvFollower.visibility = View.VISIBLE

                val userAdapter = UserAdapter(it)
                followerBinding.rvFollower.adapter = userAdapter

            }
        })
    }

    private fun setViewModelProvider() {
        followersViewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
    }

    private fun showFollower() {
        followerBinding.rvFollower.setHasFixedSize(true)
        followerBinding.rvFollower.layoutManager = LinearLayoutManager(context)
        followerBinding.rvFollower.adapter = UserAdapter(listOf())
    }

    companion object {
        private const val USERNAME = "username"
        fun newInstance(username: String): Fragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }

    }
}