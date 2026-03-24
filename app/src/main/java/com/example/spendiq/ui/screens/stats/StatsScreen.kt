package com.example.spendiq.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spendiq.viewmodel.TransactionViewModel
import com.example.spendiq.ui.components.PieChart
import com.example.spendiq.ui.components.CategoryExpensePieChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    navController: NavController
) {
    val transactionViewModel: TransactionViewModel = viewModel()

    val totalIncome by transactionViewModel.totalIncome.collectAsState()
    val totalExpense by transactionViewModel.totalExpense.collectAsState()
    val expenseByCategory by transactionViewModel
        .expenseByCategory
        .collectAsState(initial = emptyList())
    expenseByCategory.forEach {
        println("Category = ${it.category}  Total = ${it.total}")
    }

    val balance = totalIncome - totalExpense

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistics") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔹 Income vs Expense Pie Chart
            if (totalIncome > 0 || totalExpense > 0) {
                PieChart(
                    income = totalIncome,
                    expense = totalExpense,
                    modifier = Modifier.size(240.dp)
                )
            } else {
                Text(
                    text = "No income / expense data",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Summary Card
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Income: ₹$totalIncome",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4CAF50)
                    )

                    Text(
                        text = "Expense: ₹$totalExpense",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFD32F2F)
                    )

                    Divider()

                    Text(
                        text = "Balance: ₹$balance",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Category-wise Expense Pie Chart
            if (expenseByCategory.isNotEmpty()) {
                Text(
                    text = "Expense by Category",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                CategoryExpensePieChart(
                    data = expenseByCategory,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}