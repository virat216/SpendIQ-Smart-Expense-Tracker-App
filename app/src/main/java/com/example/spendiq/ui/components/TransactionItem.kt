package com.example.spendiq.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spendiq.data.model.TransactionEntity
import com.example.spendiq.data.model.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(
    transaction: TransactionEntity,
    onDelete: (TransactionEntity) -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete(transaction)
                true
            } else false
        }
    )

    val shape = MaterialTheme.shapes.medium

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // ⭐ padding moved here
    ) {

        SwipeToDismissBox(
            state = dismissState,

            backgroundContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape) // ⭐ MATCH CARD SHAPE
                        .background(Color(0xFFD32F2F))
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        ) {

            Card(
                shape = shape, // ⭐ SAME SHAPE
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = transaction.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = transaction.category,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text =
                            if (transaction.type == TransactionType.EXPENSE)
                                "- ₹${transaction.amount}"
                            else
                                "+ ₹${transaction.amount}",

                        color =
                            if (transaction.type == TransactionType.EXPENSE)
                                Color(0xFFD32F2F)
                            else
                                Color(0xFF2E7D32),

                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
