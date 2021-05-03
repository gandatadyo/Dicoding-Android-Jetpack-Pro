package com.app.androidjetpack.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.androidjetpack.databinding.ActivityMainBinding
import com.app.androidjetpack.ui.detail.DetailMovieActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            startActivity(Intent(this,DetailMovieActivity::class.java))
        }
    }
}