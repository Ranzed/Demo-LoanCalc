package com.ranzed.testloancalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ranzed.testloancalculator.store.LoanAction
import com.ranzed.testloancalculator.store.LoanState
import com.ranzed.testloancalculator.ui.components.CalculatorCard
import com.ranzed.testloancalculator.ui.components.SummaryCard
import com.ranzed.testloancalculator.ui.theme.DarkLabelText
import com.ranzed.testloancalculator.ui.theme.DarkMainBg
import com.ranzed.testloancalculator.ui.theme.DarkValueText
import com.ranzed.testloancalculator.ui.theme.LightLabelText
import com.ranzed.testloancalculator.ui.theme.LightMainBg
import com.ranzed.testloancalculator.ui.theme.LightValueText
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.util.Locale

@Composable
fun CalculatorScreen(
    state: LoanState,
    onAction: (LoanAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) DarkMainBg else LightMainBg
    val labelColor = if (darkTheme) DarkLabelText else LightLabelText
    val valueColor = if (darkTheme) DarkValueText else LightValueText
    
    val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ' '
    })
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Calculator Card
        CalculatorCard(
            amount = state.amount,
            period = state.period,
            isAgreed = state.isAgreed,
            isLoading = state.isLoading,
            onAmountChange = { onAction(LoanAction.ChangeAmount(it)) },
            onPeriodChange = { onAction(LoanAction.ChangePeriod(it)) },
            onToggleAgreement = { onAction(LoanAction.ToggleAgreement) },
            onSubmit = { onAction(LoanAction.Submit) },
            labelColor = labelColor,
            valueColor = valueColor,
            darkTheme = darkTheme,
            numberFormat = numberFormat
        )
        
        // Summary Card
        SummaryCard(
            totalRepayment = state.totalRepayment,
            period = state.period,
            returnDate = state.returnDate,
            interest = state.interest,
            commission = state.commission
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    TestLoanCalculatorTheme {
        CalculatorScreen(
            state = LoanState(
                amount = 10000,
                period = 14,
                interest = 1400.0,
                commission = 100.0,
                totalRepayment = 11500.0,
                returnDate = LocalDate.now().plusDays(14),
                isAgreed = true
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = LoanState(
                amount = 5000,
                period = 7,
                interest = 350.0,
                commission = 50.0,
                totalRepayment = 5400.0,
                returnDate = LocalDate.now().plusDays(7),
                isAgreed = false
            ),
            onAction = {}
        )
    }
}
