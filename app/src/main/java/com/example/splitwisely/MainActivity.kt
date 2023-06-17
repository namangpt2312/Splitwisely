package com.example.splitwisely

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwisely.adapters.GroupAdapter
import com.example.splitwisely.adapters.IGroupAdapter
import com.example.splitwisely.daos.GroupDao
import com.example.splitwisely.models.Group
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), IGroupAdapter {

    private lateinit var groupDao: GroupDao
    private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, CreateGroupActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        groupDao = GroupDao()
        val groupCollections = groupDao.groupCollections
        val query = groupCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Group>().setQuery(query, Group::class.java).build()

        adapter = GroupAdapter(recyclerViewOptions, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.myPolls -> {
//                val intent = Intent(this, MyPostsActivity :: class.java)
//                startActivity(intent)
//            }
//            R.id.profile -> {
//                startActivity(Intent(this, SignUpActivity :: class.java))
//            }
//            R.id.signOut -> {
//                Firebase.auth.signOut()
//                startActivity(
//                    Intent(this, SplashActivity:: class.java)
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onItemClicked(postId: String) {
//        groupDao.updateOption1(postId)
        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
    }
}