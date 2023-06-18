package com.example.splitwisely

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwisely.adapters.ExpenseAdapter
import com.example.splitwisely.adapters.GroupAdapter
import com.example.splitwisely.adapters.IExpenseAdapter
import com.example.splitwisely.adapters.IGroupAdapter
import com.example.splitwisely.auth.SignUpActivity
import com.example.splitwisely.daos.ExpenseDao
import com.example.splitwisely.daos.GroupDao
import com.example.splitwisely.models.Expense
import com.example.splitwisely.models.Group
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class GroupActivity : AppCompatActivity(), IExpenseAdapter {

    private lateinit var expenseDao: ExpenseDao
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val groupId = intent.getStringExtra("groupId")
            val intent = Intent(this, CreateExpenseActivity::class.java)
            intent.putExtra("groupId", groupId)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
//        expenseDao = ExpenseDao()
//        val expenseCollections = expenseDao.expenseCollections
        val groupId = intent.getStringExtra("groupId")!!
        val query = FirebaseFirestore.getInstance().collection("groupExpenses").document(groupId).collection("expenses").orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Expense>().setQuery(query, Expense::class.java).build()

        adapter = ExpenseAdapter(recyclerViewOptions, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onItemClicked(expenseId: String) {
//        groupDao.updateOption1(postId)
        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
    }
}