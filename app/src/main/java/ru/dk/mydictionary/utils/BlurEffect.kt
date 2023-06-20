package ru.dk.mydictionary.utils

import androidx.lifecycle.LiveData

class BlurEffect() : LiveData<Float>() {
    init {
        postValue(0.01f)
    }

    fun setBlur(float: Float) {
        postValue(float)
    }
}