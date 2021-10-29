package com.veronica.idn.githubapp.presentation.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.databinding.ActivityFavoriteBinding
import com.veronica.idn.githubapp.presentation.detail.DetailActivity
import com.veronica.idn.githubapp.presentation.home.UserAdapter
import com.veronica.idn.githubapp.util.OnItemClickCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        setRecyclerView()
        customAppBar()
        setViewModelProvider()
        observerData()
    }

    private fun observerData() {
        favoriteViewModel.favoriteUserLiveData.observe(this, {
            if (it?.isEmpty() == true) {
                favoriteBinding.llFavoriteNotFound.visibility = View.VISIBLE
                favoriteBinding.rvFavorite.visibility = View.GONE
            } else {
                favoriteBinding.llFavoriteNotFound.visibility = View.GONE
                favoriteBinding.rvFavorite.visibility = View.VISIBLE
                val userAdapter = UserAdapter(it?.map { detailUser ->
                    User(detailUser.login, detailUser.avatar_url ?: "", html_url = "", type = "")
                })
                userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onItemClicked(user: User?) {
                        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_DATA, user)
                        startActivity(intent)
                    }

                })
                favoriteBinding.rvFavorite.adapter = userAdapter
            }
        })
    }

    private fun setViewModelProvider() {
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private fun customAppBar() {
        title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setRecyclerView() {
        favoriteBinding.rvFavorite.setHasFixedSize(true)
        favoriteBinding.rvFavorite.layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavorite.adapter = UserAdapter(mutableListOf())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUser()
    }

}