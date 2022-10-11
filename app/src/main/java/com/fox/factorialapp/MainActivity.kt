package com.fox.factorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fox.factorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}