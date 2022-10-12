package com.fox.factorialapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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

    @SuppressLint("ServiceCast")
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
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
                is Factorial -> {
                    binding.textView.text = it.value
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}