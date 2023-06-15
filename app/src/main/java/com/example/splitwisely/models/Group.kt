package com.example.splitwisely.models

data class Group(
    val name: String = "",
    val imageUrl: String = "",
    val uid: String = "",
    val createdBy: String = "",
    val createdAt: Long = 0L,
    val groupMembers: ArrayList<String> = ArrayList()
)
