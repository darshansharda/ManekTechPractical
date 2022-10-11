package com.manektechpractical.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "your_transaction")
data class TransactionDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var withDrawnAmount: Int = 0,
    var note100: Int = 0,
    var note200: Int = 0,
    var note500: Int = 0,
    var note2000: Int = 0,
)
