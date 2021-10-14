package com.veronica.idn.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.veronica.idn.githubapp.databinding.ActivityDetailBinding
import com.veronica.idn.githubapp.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
    }
}