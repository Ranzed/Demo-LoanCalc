package com.ranzed.testloancalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranzed.testloancalculator.ui.theme.ButtonGradientStart
import com.ranzed.testloancalculator.ui.theme.DarkLabelText
import com.ranzed.testloancalculator.ui.theme.DarkMainBg
import com.ranzed.testloancalculator.ui.theme.LightLabelText
import com.ranzed.testloancalculator.ui.theme.LightMainBg
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme

@Composable
fun GetMoneySection(
    isAgreed: Boolean,
    isLoading: Boolean,
    onToggleAgreement: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = Color(0xFF1C1C1E)
    val textColor = Color(0xFF8E8E93)
    val labelColor = DarkLabelText

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor, RoundedCornerShape(30.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GradientButton(
            modifier = Modifier.height(73.dp),
            text = "Get money",
            onClick = onSubmit,
            enabled = isAgreed,
            isLoading = isLoading,
            useGradient = true
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = isAgreed,
                onCheckedChange = { onToggleAgreement() },
                colors = CheckboxDefaults.colors(
                    checkedColor = ButtonGradientStart,
                    uncheckedColor = textColor.copy(alpha = 0.5f),
                    checkmarkColor = Color.Black
                ),
            )
            Text(
                text = "I have read and agree to the following",
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 40.dp, height = 16.dp)
                .border(
                    width = 1.dp,
                    color = labelColor.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = labelColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GetMoneySectionPreview() {
    TestLoanCalculatorTheme {
        GetMoneySection(
            isAgreed = true,
            isLoading = false,
            onToggleAgreement = {},
            onSubmit = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetMoneySectionUncheckedPreview() {
    TestLoanCalculatorTheme {
        GetMoneySection(
            isAgreed = false,
            isLoading = false,
            onToggleAgreement = {},
            onSubmit = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetMoneySectionLoadingPreview() {
    TestLoanCalculatorTheme {
        GetMoneySection(
            isAgreed = true,
            isLoading = true,
            onToggleAgreement = {},
            onSubmit = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GetMoneySectionLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        GetMoneySection(
            isAgreed = true,
            isLoading = false,
            onToggleAgreement = {},
            onSubmit = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
