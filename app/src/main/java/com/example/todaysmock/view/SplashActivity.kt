package com.example.todaysmock.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.todaysmock.R
import com.example.todaysmock.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAnimation()
    }
    private fun initAnimation() {   try {
        val imageAnimation = AnimationUtils.loadAnimation(this, R.anim.toptocenter)
        binding.ivLogo.animation = imageAnimation
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.bottomtocenter)
        binding.tvText.animation = textAnimation
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,StartQuizActivity::class.java))
            finish()
        }, 3000)
    }catch(e:Exception){
        e.printStackTrace()
    }
    }
}