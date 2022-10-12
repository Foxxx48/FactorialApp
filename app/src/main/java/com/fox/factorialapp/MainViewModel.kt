package com.fox.factorialapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.coroutines.coroutineContext

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val coroutineScope =
        CoroutineScope(Dispatchers.Main + CoroutineName("My coroutine scope"))


    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        coroutineScope.launch {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(result)
            Log.d("MainViewModel", coroutineContext.toString())
        }
    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {

            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()

    }


//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            var result = BigInteger.ONE
//            for (i in 1..number) {
//                println(result.multiply(BigInteger.valueOf(i)))
//                result *= BigInteger.valueOf(i)
//            }
//            it.resumeWith(Result.success(result.toString()))
//
//        }
//
//    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}