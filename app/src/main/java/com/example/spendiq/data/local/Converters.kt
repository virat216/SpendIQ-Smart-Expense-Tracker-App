package com.example.spendiq.data.local

import androidx.room.TypeConverter
import com.example.spendiq.data.model.TransactionType

class Converters {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}
