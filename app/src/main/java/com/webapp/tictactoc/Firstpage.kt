package com.webapp.tictactoc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.webapp.tictactoc.databinding.ActivityFirstpageBinding

var singleUser = false

class Firstpage : AppCompatActivity() {
    private lateinit var binding: ActivityFirstpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstpageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button11.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            singleUser = true;
        }
        binding.button12.setOnClickListener {
            startActivity(Intent(this, SecondPage::class.java))
            singleUser = false;
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
    }
}
