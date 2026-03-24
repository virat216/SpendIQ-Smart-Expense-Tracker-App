package com.example.spendiq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendiq.data.local.SpendIQDatabase
import com.example.spendiq.data.model.TransactionEntity
import com.example.spendiq.data.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) :
    AndroidViewModel(application) {

    // ---------- Database ----------
    private val dao = SpendIQDatabase
        .getDatabase(application)
        .transactionDao()

    private val repository = TransactionRepository(dao)

    private val sharingStarted = SharingStarted.WhileSubscribed(5_000)

    // ---------- All Transactions ----------
    val transactions = repository
        .getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = emptyList()
        )

    // ---------- Total Income ----------
    val totalIncome = repository
        .getTotalIncome()
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = 0.0
        )

    // ---------- Total Expense ----------
    val totalExpense = repository
        .getTotalExpense()
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = 0.0
        )

    // ---------- Expense By Category ----------
    val expenseByCategory = repository
        .getExpenseByCategory()     // ⭐ Repository handles filtering EXPENSE
        .stateIn(
            scope = viewModelScope,
            started = sharingStarted,
            initialValue = emptyList()
        )

    // ---------- Add Transaction ----------
    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    // ---------- Delete + Undo ----------
    private var recentlyDeletedTransaction: TransactionEntity? = null

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            recentlyDeletedTransaction = transaction
            repository.delete(transaction.id)
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            recentlyDeletedTransaction?.let {
                repository.insert(it)
                recentlyDeletedTransaction = null
            }
        }
    }
}
