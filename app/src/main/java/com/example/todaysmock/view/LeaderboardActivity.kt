package com.example.todaysmock.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todaysmock.R
import com.example.todaysmock.databinding.ActivityLeaderboardBinding

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLeaderboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}