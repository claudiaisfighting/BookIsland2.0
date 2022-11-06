package com.example.bookisland20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnToAc3 = findViewById<Button>(R.id.btnToSearch)
        btnToAc3.setOnClickListener{
            val Intent = Intent(this, MainActivity3::class.java)
            startActivity(Intent)
        }

    }
}