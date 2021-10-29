package com.veronica.idn.githubapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.veronica.idn.githubapp.databinding.ActivitySplashBinding
import com.veronica.idn.githubapp.presentation.home.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        goHomeScreen()
    }

    private fun goHomeScreen() {
        Handler(mainLooper).postDelayed({
            startActivity(MainActivity.getLaunchService(this))
            finish()
        }, 3000)
    }
}