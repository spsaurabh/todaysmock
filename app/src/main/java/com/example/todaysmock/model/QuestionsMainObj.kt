package com.example.todaysmock.model

import com.google.gson.annotations.SerializedName

class QuestionsMainObj {
    @SerializedName("response_code" ) var responseCode : Int?               = null
    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf()

}