package com.example.todaysmock.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaysmock.R
import com.example.todaysmock.adapter.OptionsAdapter
import com.example.todaysmock.constants.Constants
import com.example.todaysmock.databinding.ActivityQuizBinding
import com.example.todaysmock.model.Results
import com.example.todaysmock.utils.isNetworkAvailable
import com.example.todaysmock.viewmodel.QuestionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class QuizActivity : BaseActivity() {
    private lateinit var binding:ActivityQuizBinding
    private var countDownTimer:CountDownTimer?=null
    private var timeRemaining: Long = 30000
    private lateinit var questionsViewModel:QuestionsViewModel
    private lateinit var questionsList:ArrayList<Results>
    private lateinit var givenAnswerList:ArrayList<String>
    private var apiJob: Job?=null
    private lateinit var answerList:ArrayList<String>
    var currentQueNo=0
    var nextQuestions=MutableLiveData<String>()
    var plusOneforCrrentQue=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initTimer()
        initViews()
        hitQuestionsAPI()
        getQuestionApiResponse()
    }
    private fun getQuestionApiResponse() {
        //Get Main Object Of API
        questionsViewModel.questionsMainObj.observe(this, Observer {
            if(it.results.isNotEmpty()){
                dismissdialog()
                questionsList.clear()
                questionsList.addAll(it.results)
                plusOneforCrrentQue = currentQueNo+1
                binding.tvcurrntqueno.text=plusOneforCrrentQue.toString()
                binding.tvoutno.text=questionsList.size.toString()
                binding.tvQuestn.text=questionsList[currentQueNo].question
                answerList.add(questionsList[currentQueNo].correctAnswer.toString())
                answerList.addAll(questionsList[currentQueNo].incorrectAnswers)
                initTimer()
                binding.rvOptions.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                binding.rvOptions.adapter=OptionsAdapter(this,answerList){no,selectedAnsw->

                }
            }else{
                dismissdialog()
            }
        })
        //handle api failure
        questionsViewModel.isApiFailure.observe(this, Observer {
            if(it){
                dismissdialog()
                snackBar(binding.root,getString(R.string.msg_pls_try_ltr))
            }
        })
        //if 30 seconds time finished increase the question number by one and display next question
        nextQuestions.observe(this, Observer {
            if(currentQueNo<questionsList.size-1){
                answerList.clear()
                currentQueNo+=1
                plusOneforCrrentQue+=1
                binding.tvQuestn.text=questionsList[currentQueNo].question
                binding.tvcurrntqueno.text=plusOneforCrrentQue.toString()
                answerList.add(questionsList[currentQueNo].correctAnswer.toString())
                answerList.addAll(questionsList[currentQueNo].incorrectAnswers)
                binding.rvOptions.adapter?.notifyDataSetChanged()
                initTimer()
            }else{
                Toast.makeText(
                    this,getString(R.string.msg_test_finishe
                    ),Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LeaderboardActivity::class.java))
            }
        })
    }
    private fun hitQuestionsAPI() {
        if(isNetworkAvailable(this)){
            startLoadingdialog(this)
            apiJob = CoroutineScope(Dispatchers.IO).launch{
                questionsViewModel.hitQuestionGetApi("${Constants.API_AMOUNT}=10")
            }
        }else{
            dismissdialog()
            snackBar(binding.root,getString(R.string.msg_chck_internet))
        }
    }
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
    override fun onStop() {
        super.onStop()
        countDownTimer?.cancel()
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        apiJob?.cancel()
        questionsList.clear()
        answerList.clear()
        binding.btnext.setOnClickListener(null)
        countDownTimer?.cancel()
        countDownTimer=null
    }
    private fun initViews() {
        givenAnswerList= ArrayList()
        questionsList=ArrayList()
        answerList=ArrayList()
        questionsViewModel = ViewModelProvider(this)[QuestionsViewModel::class.java]
        binding.btnext.setOnClickListener{

        }
    }
    private fun initTimer() {
        val totalTime = 30 * 1000 // 30 seconds in milliseconds
        val interval = 1000 // 1 second interval
//        binding.pb.max=totalTime/interval
        countDownTimer = object : CountDownTimer(totalTime.toLong(), interval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / interval).toInt()
                timeRemaining = millisUntilFinished

//                binding.pb.progress = binding.pb.max-progress
                binding.tvTimer.text = (progress + 1).toString() // Show countdown in reverse manner
            }
            override fun onFinish() {
                binding.tvTimer.text = "0"
                nextQuestions.value="next"
            }
        }
        countDownTimer?.start()
    }
}