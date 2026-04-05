package com.ranzed.testloancalculator

import android.app.Application
import android.util.Log

class MyApplication : Application {

    constructor() {
        Log.d("INIT_TEST", "MyApplication.constructor")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("INIT_TEST", "MyApplication.onCreate")
    }
}