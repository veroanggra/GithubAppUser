package com.veronica.idn.githubapp.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veronica.idn.githubapp.presentation.favorite.FavoriteActivity
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.databinding.ActivityMainBinding
import com.veronica.idn.githubapp.presentation.detail.DetailActivity
import com.veronica.idn.githubapp.util.OnItemClickCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var isDark: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        showRecycler()
        setViewModelProvider()
        observeData()
        searchUser()
        observeMode()
        error()
        loading()

    }

    private fun loading() {
        mainViewModel.loading.observe(this, { isLoading ->
            if (isLoading) {
                mainBinding.pbMain.visibility = View.VISIBLE
            } else {
                mainBinding.pbMain.visibility = View.GONE
            }
        })
    }

    private fun searchUser() {
        mainBinding.etSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                mainViewModel.getSearchUser(p0)
                try {
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(mainBinding.root.windowToken, 0)
                } catch (e: Throwable) {
                    Log.v("DEBUG", e.toString())
                }
                return true

            }

            override fun onQueryTextChange(p0: String): Boolean {
                mainViewModel.getSearchUser(p0)
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        inflater.inflate(R.menu.language, menu)
        return true
    }

    private fun observeData() {
        mainViewModel.listUserLiveData.observe(this, { users ->
            if ((users?.size ?: 0) == 0) {
                mainBinding.llMainNotFound.visibility = View.VISIBLE
                mainBinding.rvMain.visibility = View.GONE
            } else {
                mainBinding.llMainNotFound.visibility = View.GONE
                mainBinding.rvMain.visibility = View.VISIBLE
                val adapter = UserAdapter(users)
                mainBinding.rvMain.adapter = adapter
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                adapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onItemClicked(user: User?) {
                        intent.putExtra(DetailActivity.EXTRA_DATA, user)
                        startActivity(intent)
                    }
                })
            }
        })
    }

    private fun observeMode() {
        mainViewModel.getThemeSetting().observe(this,
            { isDark: Boolean ->
                if (isDark) {
                    this.isDark = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    this.isDark = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

    private fun setViewModelProvider() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun error() {
        mainViewModel.error.observe(this, {
            if (it == null) {
                mainBinding.llMainError.visibility = View.GONE
                mainBinding.rvMain.visibility = View.VISIBLE
            } else {
                mainBinding.llMainError.visibility = View.VISIBLE
                mainBinding.rvMain.visibility = View.GONE
            }
        })
    }

    private fun showRecycler() {
        mainBinding.rvMain.setHasFixedSize(true)
        mainBinding.rvMain.layoutManager = LinearLayoutManager(this)
        mainBinding.rvMain.adapter = UserAdapter(listOf())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)

            }
            R.id.action_change_settings -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.action_change_mode -> {
                mainViewModel.saveThemeSetting(!isDark)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun getLaunchService(from: Context) =
            Intent(from, MainActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            }
    }
}