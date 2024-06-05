package com.example.todaysmock.model

data class Item(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)
