package com.veronica.idn.githubapp.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.adapter.ViewPagerAdapter
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.databinding.ActivityDetailBinding
import com.veronica.idn.githubapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(detailBinding.root)
        getDataObject()
        setViewModelProvider()
        observeData()
        loading()
        setError()
        setViewPager()
    }

    private fun setViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.username = user?.login
        detailBinding.vpDetail.adapter = viewPagerAdapter
        val tabLayout: TabLayout = detailBinding.tlDetail
        TabLayoutMediator(tabLayout, detailBinding.vpDetail) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun setError() {
        detailViewModel.error.observe(this, {
            if (it == null) {
                detailBinding.llDetailError.visibility = View.GONE
                detailBinding.clDetail.visibility = View.VISIBLE
            } else {
                detailBinding.llDetailError.visibility = View.VISIBLE
                detailBinding.clDetail.visibility = View.GONE
            }
        })
    }

    private fun loading() {
        detailViewModel.loading.observe(this, { isLoading ->
            if (isLoading) {
                detailBinding.pbDetail.visibility = View.VISIBLE
            } else {
                detailBinding.pbDetail.visibility = View.GONE
            }
        })
    }

    private fun observeData() {
        detailViewModel.getDetailUser(user?.login ?: "")
        detailViewModel.detailUser.observe(this, { detailUser ->
            Glide.with(this).load(detailUser?.avatar_url)
                .apply(RequestOptions().centerCrop().override(100))
                .into(detailBinding.cvItemDetailUser)
            detailBinding.tvNameDetail.text = detailUser?.name ?: getString(R.string.no_available)
            detailBinding.tvUsernameDetail.text =
                detailUser?.login ?: getString(R.string.no_available)
            detailBinding.tvLocationDetail.text =
                detailUser?.location ?: getString(R.string.no_available)
            detailBinding.tvDescriptionDetail.text =
                detailUser?.company ?: getString(R.string.no_available)
            detailBinding.tvFollowersDetail.text = detailUser?.followers.toString()
            detailBinding.tvFollowingDetail.text = detailUser?.following.toString()
            detailBinding.tvEmailDetail.text = detailUser?.email ?: getString(R.string.no_available)

            detailViewModel.showFavoriteUser(detailUser)
            detailBinding.ivFavDetail.setOnClickListener {
                detailViewModel.isFavoriteItem(detailUser)
            }

            detailViewModel.isFavorite.observe(this, { isFav ->
                if (isFav) {
                    detailBinding.ivFavDetail.setImageResource(R.drawable.ic_baseline_favorite_red)
                } else {
                    detailBinding.ivFavDetail.setImageResource(R.drawable.ic_outline_favorite_border_red)
                }
            })
        })
    }

    private fun getDataObject() {
        user = intent.getParcelableExtra(EXTRA_DATA)
    }

    private fun setViewModelProvider() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.txt_followers,
            R.string.txt_following
        )

    }
}