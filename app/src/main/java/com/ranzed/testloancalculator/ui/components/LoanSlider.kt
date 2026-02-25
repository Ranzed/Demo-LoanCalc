package com.ranzed.testloancalculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.tan

enum class SliderType {
    AMOUNT,
    PERIOD
}

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun LoanSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    type: SliderType,
    modifier: Modifier = Modifier,
    steps: Int = 0
) {
    var sliderPosition by remember { mutableFloatStateOf(value) }
    var isDragging by remember { mutableStateOf(false) }
    val sliderFlow = remember { MutableStateFlow(value) }
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    if (!isDragging) {
        sliderPosition = value
    }

    LaunchedEffect(Unit) {
        sliderFlow
            .debounce(300L)
            .distinctUntilChanged()
            .collect { currentOnValueChange(it) }
    }

    val valueGap = 6.dp
    val sliderContainerHeight = 32.dp
    val trackHeight = 8.dp
    val activeTrackHeight = 11.dp
    val thumbColor = when (type) {
        SliderType.AMOUNT -> Color(0xFF8BC34A)
        SliderType.PERIOD -> Color(0xFFFFB300)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(valueGap)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sliderContainerHeight),
        ) {
            androidx.compose.foundation.Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(activeTrackHeight)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.Center)
            ) {
                val inactiveTrackHeightPx = trackHeight.toPx()
                val inactiveTrackTop = (size.height - inactiveTrackHeightPx) / 2f

                val stripeColor = Color(0xFFD3D3D3)
                val stripeAngleDegrees = 60f
                val stripeStrokeWidth = 8f
                val stripeGap = 4f
                val stripeDx = (inactiveTrackHeightPx / tan(stripeAngleDegrees * PI.toFloat() / 180f))
                val ext = stripeStrokeWidth
                val extDx = ext * stripeDx / inactiveTrackHeightPx
                var startX = -stripeDx - extDx

                clipRect(
                    left = 0f,
                    top = inactiveTrackTop,
                    right = size.width,
                    bottom = inactiveTrackTop + inactiveTrackHeightPx
                ) {
                    while (startX < size.width + stripeDx + extDx) {
                        drawLine(
                            color = stripeColor,
                            start = Offset(startX - extDx, inactiveTrackTop + inactiveTrackHeightPx + ext),
                            end = Offset(startX + stripeDx + extDx, inactiveTrackTop - ext),
                            strokeWidth = stripeStrokeWidth,
                        )
                        startX += stripeDx + stripeGap
                    }
                }

                val progress = ((sliderPosition - valueRange.start) / (valueRange.endInclusive - valueRange.start))
                    .coerceIn(0f, 1f)
                val filledWidth = size.width * progress
                val activeTrackBrush = when (type) {
                    SliderType.AMOUNT -> Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF8FD400),
                            Color(0xFFA3D827)
                        ),
                        startX = 0f,
                        endX = filledWidth.coerceAtLeast(1f)
                    )
                    SliderType.PERIOD -> Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFFC12B),
                            Color(0xFFFFA000)
                        ),
                        startX = 0f,
                        endX = filledWidth.coerceAtLeast(1f)
                    )
                }

                val activeTrackHeightPx = activeTrackHeight.toPx()
                val activeTrackTop = (size.height - activeTrackHeightPx) / 2f
                val activeCorner = CornerRadius(activeTrackHeightPx / 2f, activeTrackHeightPx / 2f)
                drawRoundRect(
                    brush = activeTrackBrush,
                    topLeft = Offset(0f, activeTrackTop),
                    size = Size(filledWidth, activeTrackHeightPx),
                    cornerRadius = activeCorner
                )
            }

            Slider(
                value = sliderPosition,
                onValueChange = {
                    isDragging = true
                    sliderPosition = it
                    sliderFlow.value = it
                },
                onValueChangeFinished = {
                    isDragging = false
                    currentOnValueChange(sliderPosition)
                },
                valueRange = valueRange,
                steps = steps,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                thumb = {
                    LoanSliderThumb(
                        type = type,
                        ringColor = thumbColor
                    )
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Transparent,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent,
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatRangeValueForLoanSlider2(valueRange.start, type),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF737272)
            )
            Text(
                text = formatRangeValueForLoanSlider2(valueRange.endInclusive, type),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF737272)
            )
        }
    }
}

@Composable
private fun LoanSliderThumb(
    type: SliderType,
    ringColor: Color
) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier.size(40.dp)
    ) {
        val outerRadius = min(size.width, size.height) / 2f
        val innerRadius = outerRadius * 0.72f
        val c = center
        val innerBrush = when (type) {
            SliderType.AMOUNT -> Brush.radialGradient(
                colors = listOf(
                    Color(0xFFD8F686),
                    Color(0xFF9AD61D)
                ),
                center = Offset(c.x - innerRadius * 0.35f, c.y - innerRadius * 0.35f),
                radius = innerRadius * 1.35f
            )
            SliderType.PERIOD -> Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFE58A),
                    Color(0xFFFFA300)
                ),
                center = Offset(c.x - innerRadius * 0.35f, c.y - innerRadius * 0.35f),
                radius = innerRadius * 1.35f
            )
        }

        drawCircle(color = ringColor, radius = outerRadius, center = c)
        drawCircle(brush = innerBrush, radius = innerRadius, center = c)
        drawCircle(
            color = Color.White.copy(alpha = 0.35f),
            radius = innerRadius * 0.30f,
            center = Offset(c.x - innerRadius * 0.28f, c.y - innerRadius * 0.28f)
        )
    }
}

private fun formatRangeValueForLoanSlider2(value: Float, type: SliderType): String {
    return when (type) {
        SliderType.AMOUNT -> {
            val numberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
            }
            numberFormat.format(value.toInt()).replace(",", " ")
        }
        SliderType.PERIOD -> value.toInt().toString()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoanSliderAmountPreview() {
    var value by remember { mutableFloatStateOf(500000f) }
    LoanSlider(
        value = value,
        onValueChange = { value = it },
        valueRange = 100000f..1000000f,
        type = SliderType.AMOUNT
    )
}
