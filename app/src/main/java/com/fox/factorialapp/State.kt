package com.fox.factorialapp

data class State(
    val isError: Boolean = false,
    val isInProgress: Boolean = false,
    val factorial: String = ""
)
