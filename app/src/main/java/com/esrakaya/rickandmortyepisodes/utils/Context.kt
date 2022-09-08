package com.esrakaya.rickandmortyepisodes.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.esrakaya.rickandmortyepisodes.R

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.showError(message: String?) {
    val errorText = message ?: getString(R.string.general_error)
    Toast.makeText(this, errorText, LENGTH_LONG).show()
}