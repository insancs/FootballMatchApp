package com.sanitcode.footballclubapp.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sanitcode.footballclubapp.util.Util

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences(Util.SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        preferences = getSharedPreferences(Util.SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    fun back() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}