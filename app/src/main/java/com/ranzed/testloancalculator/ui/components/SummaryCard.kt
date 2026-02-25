package com.ranzed.testloancalculator.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import com.ranzed.testloancalculator.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ranzed.testloancalculator.ui.theme.SummaryBgGradientEnd
import com.ranzed.testloancalculator.ui.theme.SummaryBgGradientStart
import com.ranzed.testloancalculator.ui.theme.SummaryBorder
import com.ranzed.testloancalculator.ui.theme.SummaryHeadingText
import com.ranzed.testloancalculator.ui.theme.SummaryInclusiveLabel
import com.ranzed.testloancalculator.ui.theme.SummaryInnerBorder
import com.ranzed.testloancalculator.ui.theme.SummarySubLabels
import com.ranzed.testloancalculator.ui.theme.SummaryValueText
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SummaryCard(
    totalRepayment: Double,
    period: Int,
    returnDate: LocalDate,
    interest: Double,
    commission: Double,
    modifier: Modifier = Modifier
) {
    val numberFormat = DecimalFormat("#,##0.##", DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ' '
    })
    val dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy")

    val backgroundBrush = Brush.linearGradient(
        colors = listOf(SummaryBgGradientStart, SummaryBgGradientEnd)
    )
    
    val overlayBrush = Brush.verticalGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.2f),
            Color.Black.copy(alpha = 0.2f)
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundBrush, RoundedCornerShape(35.dp))
            .background(overlayBrush, RoundedCornerShape(35.dp))
            .border(1.dp, SummaryBorder, RoundedCornerShape(35.dp))
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.7.dp,
                        SummaryInnerBorder,
                        RoundedCornerShape(30.dp))
                    .padding(start = 30.dp, top = 30.dp, end = 12.dp, bottom = 30.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_summary_coin),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Column {
                        Text(
                            text = "The amount to return:",
                            color = SummaryHeadingText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AnimatedContent(
                            targetState = totalRepayment,
                            label = "totalRepaymentValue"
                        ) { value ->
                            Text(
                                text = "$${numberFormat.format(value)}",
                                color = SummaryValueText,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.7.dp,
                        SummaryInnerBorder,
                        RoundedCornerShape(30.dp))
                    .padding(start = 30.dp, top = 30.dp, end = 12.dp, bottom = 30.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_loan_term),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Column {
                        Text(
                            text = "Loan term:",
                            color = SummaryHeadingText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AnimatedContent(
                            targetState = period,
                            label = "periodValue"
                        ) { value ->
                            Text(
                                text = "for $value days",
                                color = SummaryValueText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = SummaryValueText, fontSize = 14.sp, fontWeight = FontWeight.Medium)) {
                                    append("Until ")
                                }
                                withStyle(SpanStyle(color = SummaryInclusiveLabel, fontSize = 14.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift(0.2f))) {
                                    append("→ ")
                                }
                                withStyle(SpanStyle(color = SummaryValueText, fontSize = 14.sp, fontWeight = FontWeight.Medium)) {
                                    append(returnDate.format(dateFormatter))
                                    append(" ")
                                }
                                withStyle(SpanStyle(color = SummaryInclusiveLabel, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)) {
                                    append("inclusive")
                                }
                            }
                        )
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.7.dp,
                        SummaryInnerBorder,
                        RoundedCornerShape(30.dp))
                    .padding(start = 30.dp, top = 30.dp, end = 12.dp, bottom = 30.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_summary_plus),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Includes:",
                            color = SummaryHeadingText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Interest:",
                                color = SummarySubLabels,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AnimatedContent(
                                        targetState = interest,
                                        label = "interestValue"
                                    ) { value ->
                                        Text(
                                            text = "$${numberFormat.format(value)}",
                                            color = SummaryValueText,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Text(
                                        text = "(Including VAT)",
                                        color = SummarySubLabels,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Text(
                                text = "Commissions:",
                                color = SummarySubLabels,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AnimatedContent(
                                        targetState = commission,
                                        label = "commissionValue"
                                    ) { value ->
                                        Text(
                                            text = "$${numberFormat.format(value)}",
                                            color = SummaryValueText,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Text(
                                        text = "(Excluding VAT)",
                                        color = SummarySubLabels,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF262626)
@Composable
private fun SummaryCardPreview() {
    TestLoanCalculatorTheme {
        SummaryCard(
            totalRepayment = 11500.0,
            period = 14,
            returnDate = LocalDate.now().plusDays(14),
            interest = 1400000.0,
            commission = 100.0,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryCardLightPreview() {
    TestLoanCalculatorTheme(darkTheme = false) {
        SummaryCard(
            totalRepayment = 5750.0,
            period = 7,
            returnDate = LocalDate.now().plusDays(7),
            interest = 350.0,
            commission = 50.0,
            modifier = Modifier.padding(16.dp)
        )
    }
}
