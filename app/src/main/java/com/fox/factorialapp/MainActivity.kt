package com.fox.factorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fox.factorialapp.Result
import com.fox.factorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModels()

        binding.button.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModels() {
        viewModel.state.observe(this) {

            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true

            when (it) {
                is Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.button.isEnabled = false
                }
                is Error -> {
                    Toast.makeText(
                        this,
                        "You did not enter a value",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Result -> {
                    binding.textView.text = it.factorial
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}