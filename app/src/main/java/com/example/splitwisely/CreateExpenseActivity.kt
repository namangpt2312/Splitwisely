package com.example.splitwisely

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.splitwisely.daos.ExpenseDao
import com.example.splitwisely.daos.GroupDao
import com.example.splitwisely.models.Expense
import com.example.splitwisely.models.Group
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class CreateExpenseActivity : AppCompatActivity() {

    val storage by lazy {
        FirebaseStorage.getInstance()
    }

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance()
    }

    val nextBtn: MaterialButton by lazy {
        findViewById<MaterialButton>(R.id.nextBtn)
    }

    val userImgView: ImageView by lazy {
        findViewById<ImageView>(R.id.userImgView)
    }

    lateinit var downloadUrl : String
    private lateinit var expenseDao: ExpenseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_expense)

        expenseDao = ExpenseDao()

        userImgView.setOnClickListener {
            checkPermissionForImage()
        }

        nextBtn.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEt).text.toString().trim()
            if(name.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else if(!::downloadUrl.isInitialized) {
                Toast.makeText(this, "Profile picture cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else {
                nextBtn.isEnabled = false

//                expenseDao.addExpense(name, downloadUrl)
//                val intent = Intent(this, GroupActivity:: class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                startActivity(intent)
//                finish()

                val currentUserId = auth.uid!!
                val currentTime = System.currentTimeMillis()
                val expense = Expense(name, downloadUrl, currentUserId, currentUserId, currentTime)
                val groupId = intent.getStringExtra("groupId")!!

                database.collection("groupExpenses").document(groupId).collection("expenses").document().set(expense).addOnSuccessListener {
                    val intent = Intent(this, GroupActivity:: class.java)
                    intent.putExtra("groupId", groupId)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    nextBtn.isEnabled = true
                }
            }
        }
    }

    private fun checkPermissionForImage() {
        if((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED))
        {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            val permissionWrite = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            requestPermissions(permission, 1001)
            requestPermissions(permissionWrite, 1002)
        }
        else
        {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 1000) {
            data?.data?.let {
                userImgView.setImageURI(it)
                uploadImage(it)
            }
        }
    }

    private fun uploadImage(it: Uri) {
        nextBtn.isEnabled = false
        val ref = storage.reference.child("uploads/" + auth.uid.toString())
        val uploadTask = ref.putFile(it)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if(!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }

            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            nextBtn.isEnabled = true

            if(task.isSuccessful) {
                downloadUrl = task.result.toString()
            }
            else {

            }
        }.addOnFailureListener {

        }
    }
}