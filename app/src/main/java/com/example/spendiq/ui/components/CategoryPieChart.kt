package com.example.spendiq.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spendiq.data.model.CategoryTotal
import kotlin.random.Random
import androidx.compose.runtime.remember


@Composable
fun CategoryExpensePieChart(
    data: List<CategoryTotal>,
    modifier: Modifier = Modifier
) {

    val total = data.sumOf { it.total }
    if (total <= 0) return

    val colors = remember(data) {
        List(data.size) {
            Color(
                red = Random.nextInt(50, 230) / 255f,
                green = Random.nextInt(50, 230) / 255f,
                blue = Random.nextInt(50, 230) / 255f
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Canvas(modifier = Modifier.size(200.dp)) {

                var startAngle = -90f

                data.forEachIndexed { index, item ->

                    val sweepAngle =
                        ((item.total / total) * 360f).toFloat()

                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = Size(size.width, size.height)
                    )

                    startAngle += sweepAngle
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Legend
        data.forEachIndexed { index, item ->

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(colors[index], CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = item.category.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
