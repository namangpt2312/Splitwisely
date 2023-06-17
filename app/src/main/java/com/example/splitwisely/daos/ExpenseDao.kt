package com.example.splitwisely.daos

import com.example.splitwisely.models.Expense
import com.example.splitwisely.models.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExpenseDao {
    val db = FirebaseFirestore.getInstance()
    val expenseCollections = db.collection("expenses")
    val auth = Firebase.auth

    fun addExpense(name: String, imageUrl: String) {
        GlobalScope.launch {
            val currentUserId = auth.uid!!
            val currentTime = System.currentTimeMillis()
            val expense = Expense(name, imageUrl, currentUserId, currentUserId, currentTime)
            expenseCollections.document().set(expense)
        }
    }

    fun getExpenseById(expenseId: String): Task<DocumentSnapshot> {
        return expenseCollections.document(expenseId).get()
    }
}