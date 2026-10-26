
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etDateOfBirth = findViewById<EditText>(R.id.etDateOfBirth)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)

        btnAnalyze.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val dobString = etDateOfBirth.text.toString().trim()

            if (name.isEmpty() || dobString.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your full name and date of birth",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val format = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            )
            format.isLenient = false

            val dob = try {
                format.parse(dobString)
            } catch (e: Exception) {
                null
            }

            if (dob == null) {
                Toast.makeText(
                    this,
                    "Enter date as dd/MM/yyyy",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val today = Calendar.getInstance()
            val birthDate = Calendar.getInstance()
            birthDate.time = dob

            if (birthDate.after(today)) {
                Toast.makeText(
                    this,
                    "Date of birth cannot be in the future",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("FULL_NAME", name)
            intent.putExtra("DATE_OF_BIRTH", dobString)
            startActivity(intent)
        }
    }
}
