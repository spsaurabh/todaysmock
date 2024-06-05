package com.example.todaysmock.view

import android.content.Intent
import android.os.Bundle
import com.example.todaysmock.R
import com.example.todaysmock.databinding.ActivityStartQuizBinding
import com.example.todaysmock.utils.checkEmpty

class StartQuizActivity : BaseActivity() {
    private lateinit var binding:ActivityStartQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStartQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() {
        binding.btnStartQuiz.setOnClickListener {
            if(!checkEmpty(binding.etname.text.toString())){
                snackBar(binding.root,getString(R.string.msg_entrname))
            }else{
                startActivity(Intent(this,QuizActivity::class.java))
            }
        }
        binding.btnViewLdbrd.setOnClickListener {
            startActivity(Intent(this,LeaderboardActivity::class.java))
        }
    }
}