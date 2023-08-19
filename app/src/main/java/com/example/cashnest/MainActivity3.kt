package com.example.cashnest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val amount = intent.getIntExtra("predictionAmount", 0)
        val textView: TextView = findViewById(R.id.textView)
        textView.text = "₩ $amount"

        val img_button = findViewById<ImageView>(R.id.imageView)

        img_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}