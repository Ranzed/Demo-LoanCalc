package com.ranzed.testloancalculator.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranzed.testloancalculator.ui.theme.CardGradientEnd
import com.ranzed.testloancalculator.ui.theme.CardGradientStart
import com.ranzed.testloancalculator.ui.theme.DarkBorder
import com.ranzed.testloancalculator.ui.theme.DarkLabelText
import com.ranzed.testloancalculator.ui.theme.DarkValueText
import com.ranzed.testloancalculator.ui.theme.LightLabelText
import com.ranzed.testloancalculator.ui.theme.LightValueText
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CalculatorCard(
    amount: Int,
    period: Int,
    isAgreed: Boolean,
    isLoading: Boolean,
    onAmountChange: (Int) -> Unit,
    onPeriodChange: (Int) -> Unit,
    onToggleAgreement: () -> Unit,
    onSubmit: () -> Unit,
    labelColor: Color,
    valueColor: Color,
    darkTheme: Boolean,
    numberFormat: NumberFormat
) {
    val cardBackground = if (darkTheme) {
        Brush.linearGradient(
            colors = listOf(CardGradientStart, CardGradientEnd)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.White, Color.White)
        )
    }
    
    var promoCode by remember { mutableStateOf("") }
    
    val cardShape = RoundedCornerShape(40.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (darkTheme) Modifier.border(1.dp, DarkBorder, cardShape)
                else Modifier
            ),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ), 
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (darkTheme) 0.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBackground)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Amount Slider
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Amount",
                        color = labelColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    AnimatedContent(
                        targetState = amount,
                        label = "amountValue"
                    ) { value ->
                        Text(
                            text = "$${numberFormat.format(value)}",
                            color = valueColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                
                LoanSlider(
                    value = amount.toFloat(),
                    onValueChange = { onAmountChange(it.toInt()) },
                    valueRange = 5000f..50000f,
                    type = SliderType.AMOUNT
                )
            }
            
            // Period Slider
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Period",
                        color = labelColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    AnimatedContent(
                        targetState = period,
                        label = "periodValue"
                    ) { value ->
                        Text(
                            text = "$value days",
                            color = valueColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                LoanSlider(
                    value = period.toFloat(),
                    onValueChange = { onPeriodChange(it.toInt()) },
                    valueRange = 7f..28f,
                    type = SliderType.PERIOD,
                    steps = 3
                )
            }
            
            // Get Money Section
            GetMoneySection(
                isAgreed = isAgreed,
                isLoading = isLoading,
                onToggleAgreement = onToggleAgreement,
                onSubmit = onSubmit
            )
            
            // Promo Code Section
            PromoCodeSection(
                promoCode = promoCode,
                onPromoCodeChange = { promoCode = it },
                onApplyClick = { /* no-op */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorCardPreview() {
    TestLoanCalculatorTheme(darkTheme = true) {
        val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ' '
        })
        
        CalculatorCard(
            amount = 25000,
            period = 14,
            isAgreed = true,
            isLoading = false,
            onAmountChange = {},
            onPeriodChange = {},
            onToggleAgreement = {},
            onSubmit = {},
            labelColor = DarkLabelText,
            valueColor = DarkValueText,
            darkTheme = true,
            numberFormat = numberFormat
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorCardLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ' '
        })
        
        CalculatorCard(
            amount = 10000,
            period = 7,
            isAgreed = false,
            isLoading = false,
            onAmountChange = {},
            onPeriodChange = {},
            onToggleAgreement = {},
            onSubmit = {},
            labelColor = LightLabelText,
            valueColor = LightValueText,
            darkTheme = false,
            numberFormat = numberFormat
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorCardLoadingPreview() {
    TestLoanCalculatorTheme {
        val darkTheme = isSystemInDarkTheme()
        val numberFormat = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ' '
        })
        
        CalculatorCard(
            amount = 50000,
            period = 28,
            isAgreed = true,
            isLoading = true,
            onAmountChange = {},
            onPeriodChange = {},
            onToggleAgreement = {},
            onSubmit = {},
            labelColor = if (darkTheme) DarkLabelText else LightLabelText,
            valueColor = if (darkTheme) DarkValueText else LightValueText,
            darkTheme = darkTheme,
            numberFormat = numberFormat
        )
    }
}
