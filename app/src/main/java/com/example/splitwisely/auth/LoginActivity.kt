package com.example.splitwisely.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.splitwisely.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hbb20.CountryCodePicker

class LoginActivity : AppCompatActivity() {

    private lateinit var phoneNumber : String
    private lateinit var countryCode : String
    val phoneNumberEt: EditText by lazy {
        findViewById<EditText>(R.id.phoneNumberEt)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val nextBtn = findViewById<MaterialButton>(R.id.nextBtn)
        phoneNumberEt.addTextChangedListener{
            nextBtn.isEnabled = !(it.isNullOrEmpty() || it.length < 10)
        }

        nextBtn.setOnClickListener {
            checkNumber()
        }
    }

    private fun checkNumber() {
        countryCode = findViewById<CountryCodePicker>(R.id.ccp).selectedCountryCodeWithPlus
        phoneNumber = countryCode + phoneNumberEt.text.toString()

        notifyUser()
    }

    private fun notifyUser() {
        MaterialAlertDialogBuilder(this).apply {
            setMessage("We will be verifying the phone number : $phoneNumber\n" +
                    "Is this OK, or would you like to edit the number?")
            setPositiveButton("Ok") { _,_ ->
                showOtpActivity()
            }
            setNegativeButton("Edit") { dialog, which ->
                dialog.dismiss()
            }
            setCancelable(false)
            create()
            show()
        }
    }

    private fun showOtpActivity() {
        startActivity(
            Intent(this, OtpActivity:: class.java).putExtra(
            PHONE_NUMBER, phoneNumber))
        finish()
    }
}