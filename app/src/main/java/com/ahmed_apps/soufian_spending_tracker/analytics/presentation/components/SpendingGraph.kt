package com.ahmed_apps.soufian_spending_tracker.analytics.presentation.components

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun SpendingGraph(
    modifier: Modifier = Modifier,
    prices: List<Double>,
    dates: List<String>,
    paddingSpace: Dp = 0.dp,
) {

    val onBackground = MaterialTheme.colorScheme.onBackground
    val background = MaterialTheme.colorScheme.background
    val primary = MaterialTheme.colorScheme.primary

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = onBackground.toArgb()
            textAlign = Paint.Align.CENTER
        }
    }

    val maxValue = prices.maxOrNull()?.roundToInt() ?: 0
    val verticalStep = maxValue / 4.0

    val yValues = List(5) { index -> index * verticalStep }

    val pointRadius = 20f
    val verticalSpacing = 100f
    val horizontalSpacing = 40f

    val coordinates = mutableListOf<PointF>()

    var index by remember {
        mutableStateOf<Int?>(null)
    }

    var clickedPoint by remember {
        mutableStateOf<PointF?>(null)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val touchedPoint = coordinates.find { point ->
                        val dx = offset.x - point.x
                        val dy = offset.y - point.y
                        sqrt(dx * dx + dy * dy) <= pointRadius
                    }
                    if (touchedPoint != null) {
                        index = coordinates.indexOf(touchedPoint)
                        clickedPoint = touchedPoint
                    } else {
                        index = null
                    }
                }
            }
    ) {

        val xAxisSpace = (size.width - paddingSpace.toPx()) / dates.size
        val yAxisSpace = size.height / yValues.size

        guideLines(
            yAxisSpace = yAxisSpace,
            paddingSpace = paddingSpace,
            yValues = yValues,
            verticalSpacing = verticalSpacing,
            horizontalSpacing = horizontalSpacing,
            onBackground = onBackground
        )

        xAxisTexts(
            xAxisSpace = xAxisSpace,
            textPaint = textPaint,
            dates = dates,
            horizontalSpacing = horizontalSpacing,
            onBackground = onBackground
        )

        yAxisTexts(
            paddingSpace = paddingSpace,
            yAxisSpace = yAxisSpace,
            yValues = yValues,
            textPaint = textPaint,
            spacing = verticalSpacing,
            onBackground = onBackground
        )

        points(
            xAxisSpace = xAxisSpace,
            yAxisSpace = yAxisSpace,
            prices = prices,
            verticalStep = verticalStep,
            verticalSpacing = verticalSpacing,
            pointRadius = pointRadius,
            horizontalSpacing = horizontalSpacing,
            coordinates = coordinates,
            onBackground = onBackground
        )

        val path = path(
            xAxisSpace = xAxisSpace,
            yAxisSpace = yAxisSpace,
            prices = prices,
            verticalStep = verticalStep,
            spacing = verticalSpacing,
            horizontalSpacing = horizontalSpacing,
            primary = primary
        )

        fillColorUnderPath(
            path = path,
            yAxisSpace = yAxisSpace,
            coordinates = coordinates,
            primary = primary
        )


        index?.let { index ->
            clickedPoint?.let { clickedPoint ->
                clickedPointText(
                    prices = prices,
                    dates = dates,
                    clickedPoint = clickedPoint,
                    index = index,
                    textPaint = textPaint,
                    verticalSpacing = verticalSpacing,
                    background = background,
                    primary = primary
                )
            }
        }
    }

}

private fun DrawScope.clickedPointText(
    prices: List<Double>,
    dates: List<String>,
    clickedPoint: PointF,
    index: Int,
    textPaint: Paint,
    verticalSpacing: Float,
    background: Color,
    primary: Color
) {

    val text = "(${dates[index]}) - $${prices[index]}"

    val xPosition = when (index) {
        0 -> -150f
        prices.size - 1 -> 170f
        else -> 0f
    }

    val textBounds = android.graphics.Rect()
    textPaint.getTextBounds(text, 0, text.length, textBounds)
    val textWidth = textBounds.width().toFloat()
    val textHeight = textBounds.height().toFloat()

    val padding = 25f
    val marginFromPoint = 30

    val rectWidth = textWidth + padding * 4
    val rectHeight = textHeight + padding * 2

    drawRoundRect(
        topLeft = Offset(
            clickedPoint.x - rectWidth / 2 - xPosition,
            clickedPoint.y - rectHeight - marginFromPoint
        ),
        size = Size(rectWidth, rectHeight),
        color = primary,
        cornerRadius = CornerRadius(16f, 16f)
    )

    drawContext.canvas.nativeCanvas.drawText(
        text,
        clickedPoint.x - xPosition,
        clickedPoint.y - (rectHeight / 2) - marginFromPoint + (textHeight / 2) - 6,
        textPaint.apply {
            textSize = 15.sp.toPx()
            color = background.toArgb()
        }
    )

    val verticalLine = Path().apply {
        reset()
        moveTo(
            clickedPoint.x - 0f,
            clickedPoint.y - rectHeight - marginFromPoint
        )
        lineTo(
            clickedPoint.x - 0f,
            size.height - verticalSpacing
        )
    }

    drawPath(
        verticalLine,
        color = primary.copy(0.6f),
        style = Stroke(
            width = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f)),
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.yAxisTexts(
    paddingSpace: Dp,
    yAxisSpace: Float,
    yValues: List<Double>,
    textPaint: Paint,
    spacing: Float,
    onBackground: Color
) {
    for (i in yValues.indices) {
        drawContext.canvas.nativeCanvas.drawText(
            "${yValues[i]}",
            paddingSpace.toPx(),
            size.height - yAxisSpace * (i) - spacing,
            textPaint.apply {
                textSize = 13.sp.toPx()
                color = onBackground.toArgb()
            },
        )
    }
}

private fun DrawScope.xAxisTexts(
    xAxisSpace: Float,
    textPaint: Paint,
    dates: List<String>,
    horizontalSpacing: Float,
    onBackground: Color
) {
    for (i in dates.indices) {
        drawContext.canvas.nativeCanvas.drawText(
            i.toString(),
            xAxisSpace * (i + 1) - horizontalSpacing,
            size.height,
            textPaint.apply {
                textSize = 13.sp.toPx()
                color = onBackground.toArgb()
            }
        )
    }
}

private fun DrawScope.guideLines(
    yAxisSpace: Float,
    paddingSpace: Dp,
    yValues: List<Double>,
    verticalSpacing: Float,
    horizontalSpacing: Float,
    onBackground: Color
) {

    for (i in yValues.indices) {
        val x = size.width
        val y = size.height - yAxisSpace * (i) - verticalSpacing
        val horizontalLine = Path().apply {
            reset()
            moveTo(
                paddingSpace.toPx() + horizontalSpacing,
                size.height - yAxisSpace * (i) - verticalSpacing
            )
            lineTo(x, y)
        }

        drawPath(
            horizontalLine,
            color = onBackground.copy(0.5f),
            style = Stroke(
                width = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f)),
                cap = StrokeCap.Round
            )
        )
    }
}

private fun DrawScope.points(
    xAxisSpace: Float,
    yAxisSpace: Float,
    prices: List<Double>,
    verticalStep: Double,
    verticalSpacing: Float,
    pointRadius: Float,
    horizontalSpacing: Float,
    coordinates: MutableList<PointF>,
    onBackground: Color
) {
    for (i in prices.indices) {
        val x1 = xAxisSpace * i + xAxisSpace - horizontalSpacing
        val y1 =
            size.height - (yAxisSpace * (prices[i].toFloat() / verticalStep.toFloat())) - verticalSpacing
        coordinates.add(PointF(x1, y1))
        drawCircle(
            color = onBackground,
            radius = pointRadius,
            center = Offset(x1, y1)
        )
    }
}

private fun DrawScope.path(
    xAxisSpace: Float,
    yAxisSpace: Float,
    prices: List<Double>,
    verticalStep: Double,
    spacing: Float,
    horizontalSpacing: Float,
    coordinates: MutableList<PointF> = mutableListOf(),
    primary: Color,
): Path {
    val path = Path().apply {
        reset()
        for (i in prices.indices) {
            val x1 = xAxisSpace * i + xAxisSpace - horizontalSpacing
            val y1 =
                size.height - (yAxisSpace * (prices[i].toFloat() / verticalStep.toFloat())) - spacing
            coordinates.add(PointF(x1, y1))
        }

        moveTo(coordinates.first().x, coordinates.first().y)
        for (i in 0 until coordinates.size - 1) {
            val startX = coordinates[i].x
            val startY = coordinates[i].y
            val endX = coordinates[i + 1].x
            val endY = coordinates[i + 1].y
            val controlPoint1X = (startX + endX) / 2
            val controlPoint2X = (startX + endX) / 2
            cubicTo(controlPoint1X, startY, controlPoint2X, endY, endX, endY)
        }
    }

    drawPath(
        path,
        color = primary,
        style = Stroke(
            width = 5f,
            cap = StrokeCap.Round
        )
    )

    return path
}

private fun DrawScope.fillColorUnderPath(
    path: Path,
    yAxisSpace: Float,
    coordinates: MutableList<PointF>,
    primary: Color
) {
    val fillPath = android.graphics.Path(path.asAndroidPath())
        .asComposePath()
        .apply {
            lineTo(coordinates.last().x, size.height)
            lineTo(coordinates.first().x, size.height)
            close()
        }
    drawPath(
        fillPath,
        brush = Brush.verticalGradient(
            listOf(
                primary,
                Color.Transparent,
            ),
            endY = size.height - yAxisSpace
        ),
    )
}
















