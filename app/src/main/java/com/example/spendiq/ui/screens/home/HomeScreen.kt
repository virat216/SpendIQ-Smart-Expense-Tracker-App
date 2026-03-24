package com.example.spendiq.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spendiq.viewmodel.TransactionViewModel
import com.example.spendiq.ui.components.TransactionItem

@Composable
fun HomeScreen(
    navController: NavController
) {
    val transactionViewModel: TransactionViewModel = viewModel()

    val transactions by transactionViewModel.transactions.collectAsState()
    val totalIncome by transactionViewModel.totalIncome.collectAsState()
    val totalExpense by transactionViewModel.totalExpense.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔹 Summary Card
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Income: ₹$totalIncome",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Expense: ₹$totalExpense",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Balance: ₹${totalIncome - totalExpense}",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (totalIncome-totalExpense < 0)
                            Color(0xFFD32F2F)
                        else
                            Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Transaction List
            if (transactions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No transactions yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp)
                ) {
                    items(transactions, key = { it.id }) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onDelete = { deletedTransaction ->
                                transactionViewModel.deleteTransaction(deletedTransaction)
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Transaction deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        transactionViewModel.addTransaction(deletedTransaction)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
