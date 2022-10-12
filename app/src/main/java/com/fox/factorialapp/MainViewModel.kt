package com.fox.factorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state



    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            val result = factorial(number)
            _state.value = Result(result.toString())
        }
    }

    private fun factorial(number: Long): BigInteger {
        var result = BigInteger.ONE
        for (i in 1 .. number) {
           result *= BigInteger.valueOf(i)
        }
        return result
    }


}