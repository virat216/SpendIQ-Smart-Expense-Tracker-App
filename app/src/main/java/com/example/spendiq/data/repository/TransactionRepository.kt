package com.example.spendiq.data.repository

import com.example.spendiq.data.local.TransactionDao
import com.example.spendiq.data.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import com.example.spendiq.data.model.CategoryTotal

class TransactionRepository(
    private val dao: TransactionDao
) {

    suspend fun insert(transaction: TransactionEntity) {
        dao.insertTransaction(transaction)
    }

    fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return dao.getAllTransactions()
    }

    suspend fun delete(id: Int) {
        dao.deleteTransaction(id)
    }

    fun getTotalIncome(): Flow<Double> = dao.getTotalIncome()

    fun getTotalExpense(): Flow<Double> = dao.getTotalExpense()

    fun getExpenseByCategory(): Flow<List<CategoryTotal>> {
        return dao.getExpenseByCategory()
    }
}
