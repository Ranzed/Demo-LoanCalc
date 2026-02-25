package com.ranzed.testloancalculator.data

import android.content.SharedPreferences

interface LoanPrefsRepository {
    fun saveSelection(amount: Int, period: Int)
    fun loadSelection(): Pair<Int, Int>?
}

class LoanPrefsRepositoryImpl(
    private val prefs: SharedPreferences
) : LoanPrefsRepository {

    override fun saveSelection(amount: Int, period: Int) {
        prefs.edit().putInt("amount", amount).putInt("period", period).apply()
    }

    override fun loadSelection(): Pair<Int, Int>? {
        if (!prefs.contains("amount")) return null
        return prefs.getInt("amount", 5_000) to prefs.getInt("period", 14)
    }
}
