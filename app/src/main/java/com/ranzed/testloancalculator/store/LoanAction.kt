package com.ranzed.testloancalculator.store

sealed interface LoanAction {
    data class ChangeAmount(val amount: Int) : LoanAction
    data class ChangePeriod(val period: Int) : LoanAction
    object ToggleAgreement : LoanAction
    object Submit : LoanAction
    object SubmitSuccess : LoanAction
    data class SubmitError(val message: String) : LoanAction
}
