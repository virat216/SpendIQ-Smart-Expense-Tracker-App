package com.example.spendiq.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    income: Double,
    expense: Double,
    modifier: Modifier = Modifier
) {
    val total = income + expense
    if (total <= 0.0) return

    val targetIncomeAngle = (income / total * 360f).toFloat()
    val targetExpenseAngle = (expense / total * 360f).toFloat()

    var startAnimation by remember { mutableStateOf(false) }

    val animatedIncomeAngle by animateFloatAsState(
        targetValue = if (startAnimation) targetIncomeAngle else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "IncomeAngle"
    )

    val animatedExpenseAngle by animateFloatAsState(
        targetValue = if (startAnimation) targetExpenseAngle else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "ExpenseAngle"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(220.dp)
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                val canvasSize = Size(size.width, size.height)

                // 🟢 Income slice
                drawArc(
                    color = Color(0xFF4CAF50),
                    startAngle = -90f,
                    sweepAngle = animatedIncomeAngle,
                    useCenter = true,
                    size = canvasSize
                )

                // 🔴 Expense slice
                drawArc(
                    color = Color(0xFFF44336),
                    startAngle = -90f + animatedIncomeAngle,
                    sweepAngle = animatedExpenseAngle,
                    useCenter = true,
                    size = canvasSize
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "₹${income - expense}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Income", color = Color(0xFF4CAF50))
            Text("Expense", color = Color(0xFFF44336))
        }
    }
}
