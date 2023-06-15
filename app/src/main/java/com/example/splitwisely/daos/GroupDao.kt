package com.example.splitwisely.daos

import com.example.splitwisely.models.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroupDao {
    val db = FirebaseFirestore.getInstance()
    val groupCollections = db.collection("groups")
    val auth = Firebase.auth

    fun addGroup(name: String, imageUrl: String) {
        GlobalScope.launch {
            val currentUserId = auth.uid!!
//            val userDao = UserDao()
//            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
//            val post = Post(text, user, currentTime, option1, option2, option3, option4)
            val group = Group(name, imageUrl, currentUserId, currentUserId, currentTime)
            groupCollections.document().set(group)
        }
    }

    fun getGroupById(groupId: String): Task<DocumentSnapshot> {
        return groupCollections.document(groupId).get()
    }
}