package com.ranzed.testloancalculator

import com.ranzed.testloancalculator.store.LoanAction
import com.ranzed.testloancalculator.store.LoanState
import com.ranzed.testloancalculator.store.loanReducer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LoanReducerTest {

    @Test
    fun `initial state calculates correctly`() {
        val state = LoanState()
        val result = loanReducer(state, LoanAction.ChangeAmount(5_000))
        
        assertEquals(5_000, result.amount)
        assertEquals(14, result.period)
        assertEquals(700.0, result.interest, 0.01)
        assertEquals(50.0, result.commission, 0.01)
        assertEquals(5_750.0, result.totalRepayment, 0.01)
    }

    @Test
    fun `reference example from spec - 10000 amount, 14 days`() {
        val state = LoanState()
        val result = loanReducer(
            loanReducer(state, LoanAction.ChangeAmount(10_000)),
            LoanAction.ChangePeriod(14)
        )
        
        assertEquals(10_000, result.amount)
        assertEquals(14, result.period)
        assertEquals(1_400.0, result.interest, 0.01)
        assertEquals(100.0, result.commission, 0.01)
        assertEquals(11_500.0, result.totalRepayment, 0.01)
    }

    @Test
    fun `change amount recalculates`() {
        val state = LoanState()
        val result = loanReducer(state, LoanAction.ChangeAmount(50_000))
        
        assertEquals(50_000, result.amount)
        assertEquals(7_000.0, result.interest, 0.01)
        assertEquals(500.0, result.commission, 0.01)
        assertEquals(57_500.0, result.totalRepayment, 0.01)
    }

    @Test
    fun `change period recalculates`() {
        val state = LoanState()
        val result = loanReducer(
            loanReducer(state, LoanAction.ChangeAmount(5_000)),
            LoanAction.ChangePeriod(28)
        )
        
        assertEquals(28, result.period)
        assertEquals(1_400.0, result.interest, 0.01)
        assertEquals(50.0, result.commission, 0.01)
        assertEquals(6_450.0, result.totalRepayment, 0.01)
    }

    @Test
    fun `toggle agreement changes state`() {
        val state = LoanState(isAgreed = false)
        val result = loanReducer(state, LoanAction.ToggleAgreement)
        
        assertTrue(result.isAgreed)
        
        val result2 = loanReducer(result, LoanAction.ToggleAgreement)
        assertFalse(result2.isAgreed)
    }

    @Test
    fun `submit requires agreement`() {
        val state = LoanState(isAgreed = false, amount = 5_000)
        val result = loanReducer(state, LoanAction.Submit)
        
        assertFalse(result.isLoading)
    }

    @Test
    fun `submit with agreement sets loading`() {
        val state = LoanState(isAgreed = true, amount = 5_000)
        val result = loanReducer(state, LoanAction.Submit)
        
        assertTrue(result.isLoading)
    }

    @Test
    fun `submit success clears loading`() {
        val state = LoanState(isLoading = true)
        val result = loanReducer(state, LoanAction.SubmitSuccess)
        
        assertFalse(result.isLoading)
    }

    @Test
    fun `submit error clears loading`() {
        val state = LoanState(isLoading = true)
        val result = loanReducer(state, LoanAction.SubmitError("Network error"))
        
        assertFalse(result.isLoading)
    }

}
