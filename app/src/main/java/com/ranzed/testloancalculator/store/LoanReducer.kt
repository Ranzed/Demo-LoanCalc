package com.ranzed.testloancalculator.store

import java.time.LocalDate

fun loanReducer(state: LoanState, action: LoanAction): LoanState = when (action) {
    is LoanAction.ChangeAmount -> recalculate(state.copy(amount = action.amount))
    is LoanAction.ChangePeriod -> recalculate(state.copy(period = action.period))
    is LoanAction.ToggleAgreement -> state.copy(isAgreed = !state.isAgreed)
    is LoanAction.Submit -> {
        if (state.isAgreed && state.amount > 0) state.copy(isLoading = true)
        else state
    }
    is LoanAction.SubmitSuccess -> state.copy(isLoading = false)
    is LoanAction.SubmitError -> state.copy(isLoading = false)
}

private fun recalculate(state: LoanState): LoanState {
    val interest = state.amount * 0.01 * state.period
    val commission = state.amount * 0.01
    return state.copy(
        interest = interest,
        commission = commission,
        totalRepayment = state.amount + interest + commission,
        returnDate = LocalDate.now().plusDays(state.period.toLong())
    )
}
