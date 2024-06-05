package com.example.todaysmock.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todaysmock.api.ApiClient
import com.example.todaysmock.model.QuestionsMainObj
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel:ViewModel() {
    var questionsMainObj=MutableLiveData<QuestionsMainObj>()
    var isApiFailure=MutableLiveData<Boolean>()
    fun hitQuestionGetApi(apiEndpoint:String){
        val apiConfig = ApiClient.getApiService()?.hitGetApiWithoutJsonParams(apiEndpoint)
        apiConfig?.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful){
                    Log.e("QuestionsViewModel","QuestionsApiObject${response.body()}")
                    val data= Gson().fromJson(response.body(),QuestionsMainObj::class.java)
                    questionsMainObj.value=data
                }else{
                    isApiFailure.value=true
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                isApiFailure.value=true
            }
        })
    }
}