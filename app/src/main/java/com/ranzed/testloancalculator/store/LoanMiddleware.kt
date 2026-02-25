package com.ranzed.testloancalculator.store

import com.ranzed.testloancalculator.data.LoanApiRepository
import com.ranzed.testloancalculator.data.LoanPrefsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LoanMiddleware(
    private val apiRepository: LoanApiRepository,
    private val prefsRepository: LoanPrefsRepository
) {
    private lateinit var getState: () -> LoanState
    private lateinit var dispatch: (LoanAction) -> Unit
    private lateinit var sendEvent: (UiEvent) -> Unit

    fun init(
        getState: () -> LoanState,
        dispatch: (LoanAction) -> Unit,
        sendEvent: (UiEvent) -> Unit
    ) {
        this.getState = getState
        this.dispatch = dispatch
        this.sendEvent = sendEvent
    }

    fun restoreState() {
        prefsRepository.loadSelection()?.let { (amount, period) ->
            dispatch(LoanAction.ChangeAmount(amount))
            dispatch(LoanAction.ChangePeriod(period))
        }
    }

    fun handleSideEffects(action: LoanAction, scope: CoroutineScope) {
        when (action) {
            is LoanAction.Submit -> {
                scope.launch {
                    val s = getState()
                    apiRepository.submitLoan(s.amount, s.period, s.totalRepayment)
                        .onSuccess {
                            dispatch(LoanAction.SubmitSuccess)
                            sendEvent(UiEvent.ShowSuccess("Loan application submitted"))
                        }
                        .onFailure {
                            dispatch(LoanAction.SubmitError(it.message ?: "Unknown error"))
                            sendEvent(UiEvent.ShowError(it.message ?: "Unknown error"))
                        }
                }
            }
            is LoanAction.ChangeAmount -> prefsRepository.saveSelection(action.amount, getState().period)
            is LoanAction.ChangePeriod -> prefsRepository.saveSelection(getState().amount, action.period)
            else -> {}
        }
    }
}
