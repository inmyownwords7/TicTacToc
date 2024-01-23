package com.webapp.tictactoc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.webapp.tictactoc.databinding.ActivitySecondPageBinding

var Online = true;

class SecondPage : AppCompatActivity() {
    private lateinit var binding: ActivitySecondPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.buttonOnline.setOnClickListener {
            startActivity(Intent(this, CodeActivity::class.java))
            singleUser = true;
            Online = true;
        }
        binding.buttonOffline.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            singleUser = false;
            Online = false;
        }
    }
}
