package com.ranzed.testloancalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranzed.testloancalculator.ui.theme.ButtonGradientEnd
import com.ranzed.testloancalculator.ui.theme.ButtonGradientStart
import com.ranzed.testloancalculator.ui.theme.ButtonText
import com.ranzed.testloancalculator.ui.theme.LightApplyButtonBg
import com.ranzed.testloancalculator.ui.theme.LightApplyButtonText
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    useGradient: Boolean = true
) {
    val darkTheme = isSystemInDarkTheme()
    
    val backgroundBrush = when {
        !useGradient && !darkTheme -> Brush.linearGradient(listOf(LightApplyButtonBg, LightApplyButtonBg))
        else -> Brush.linearGradient(
            colors = listOf(ButtonGradientStart, ButtonGradientEnd)
        )
    }
    
    val textColor = if (!useGradient && !darkTheme) LightApplyButtonText else ButtonText

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = textColor,
                    modifier = Modifier.height(48.dp),
                )
            } else {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GradientButtonPreview() {
    TestLoanCalculatorTheme {
        GradientButton(
            text = "Get money",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GradientButtonLoadingPreview() {
    TestLoanCalculatorTheme {
        GradientButton(
            text = "Get money",
            onClick = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GradientButtonLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        GradientButton(
            text = "Apply",
            onClick = {},
            useGradient = false
        )
    }
}
