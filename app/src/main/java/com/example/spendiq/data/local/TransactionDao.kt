package com.example.spendiq.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spendiq.data.model.TransactionEntity
import com.example.spendiq.data.model.CategoryTotal
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Int)

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'INCOME'")
    fun getTotalIncome(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE'")
    fun getTotalExpense(): Flow<Double>

    // 🔥 NEW: Category-wise expense for PieChart
    @Query("""
        SELECT category, SUM(amount) AS total
        FROM transactions
        WHERE type = 'EXPENSE'
        GROUP BY TRIM (LOWER(category))
    """)
    fun getExpenseByCategory(): Flow<List<CategoryTotal>>
}
