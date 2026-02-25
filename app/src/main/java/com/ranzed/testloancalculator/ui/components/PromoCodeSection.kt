package com.ranzed.testloancalculator.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ranzed.testloancalculator.ui.theme.DarkPromoBg
import com.ranzed.testloancalculator.ui.theme.DarkSecondaryText
import com.ranzed.testloancalculator.ui.theme.LightPromoBg
import com.ranzed.testloancalculator.ui.theme.LightPromoBorder
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme

@Composable
fun PromoCodeSection(
    promoCode: String,
    onPromoCodeChange: (String) -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) DarkPromoBg else LightPromoBg
    val borderColor = if (darkTheme) DarkSecondaryText else LightPromoBorder

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val cornerRadius = 20.dp.toPx()
                val strokeWidth = 2.dp.toPx()
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                
                drawRoundRect(
                    color = backgroundColor,
                    cornerRadius = CornerRadius(cornerRadius)
                )
                
                drawRoundRect(
                    color = borderColor,
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = pathEffect
                    )
                )
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = promoCode,
            onValueChange = onPromoCodeChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Promo code →",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = true
        )
        
        GradientButton(
            text = "Apply",
            onClick = onApplyClick,
            modifier = Modifier
                .width(93.dp)
                .height(49.dp),
            useGradient = darkTheme
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PromoCodeSectionPreview() {
    TestLoanCalculatorTheme {
        PromoCodeSection(
            promoCode = "",
            onPromoCodeChange = {},
            onApplyClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PromoCodeSectionLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        PromoCodeSection(
            promoCode = "PROMO123",
            onPromoCodeChange = {},
            onApplyClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
