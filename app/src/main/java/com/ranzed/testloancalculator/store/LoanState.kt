package com.ranzed.testloancalculator.store

import java.time.LocalDate

data class LoanState(
    val amount: Int = 5_000,
    val period: Int = 14,
    val interestRate: Double = 0.01,
    val interest: Double = 0.0,
    val commission: Double = 0.0,
    val totalRepayment: Double = 0.0,
    val returnDate: LocalDate = LocalDate.now(),
    val isAgreed: Boolean = false,
    val isLoading: Boolean = false
)
