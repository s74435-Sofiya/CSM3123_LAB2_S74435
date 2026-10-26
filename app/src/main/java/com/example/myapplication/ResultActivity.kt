
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val name = intent.getStringExtra("FULL_NAME") ?: ""
        val dobString = intent.getStringExtra("DATE_OF_BIRTH") ?: ""

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvCharacterCount =
            findViewById<TextView>(R.id.tvCharacterCount)
        val tvVowels = findViewById<TextView>(R.id.tvVowels)
        val tvRepeated = findViewById<TextView>(R.id.tvRepeated)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvDayOfBirth =
            findViewById<TextView>(R.id.tvDayOfBirth)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val cleanName = name.replace(" ", "")
        val characterCount = cleanName.length

        val vowelCount = name.filter {
            it.lowercaseChar() in "aeiou"
        }.count()

        val repeatedCharacters = cleanName
            .lowercase(Locale.ROOT)
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .keys
            .sorted()
            .joinToString(", ")
            .ifEmpty { "None" }

        val dateFormat =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        val dob = dateFormat.parse(dobString)!!

        val birthCalendar = Calendar.getInstance()
        birthCalendar.time = dob

        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) -
                birthCalendar.get(Calendar.YEAR)

        val birthdayThisYear = Calendar.getInstance()
        birthdayThisYear.set(
            today.get(Calendar.YEAR),
            birthCalendar.get(Calendar.MONTH),
            birthCalendar.get(Calendar.DAY_OF_MONTH)
        )

        if (today.before(birthdayThisYear)) {
            age--
        }

        val dayOfBirth = SimpleDateFormat(
            "EEEE",
            Locale.getDefault()
        ).format(dob)

        tvName.text = "Full Name: $name"
        tvCharacterCount.text =
            "Characters excluding spaces: $characterCount"
        tvVowels.text = "Number of vowels: $vowelCount"
        tvRepeated.text = "Repeated characters: $repeatedCharacters"
        tvAge.text = "Current age: $age"
        tvDayOfBirth.text = "Day of birth: $dayOfBirth"

        btnBack.setOnClickListener {
            finish()
        }
    }
}
