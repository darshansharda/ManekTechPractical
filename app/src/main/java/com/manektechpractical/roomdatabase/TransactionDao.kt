package com.manektechpractical.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
public interface TransactionDao {

    @Insert
    fun insertTransaction(transaction: TransactionDetails)

    @Query("select * from `your_transaction` order by id desc")
    fun getAllTransaction(): List<TransactionDetails>

    @Query("select SUM(withDrawnAmount) from `your_transaction`")
    fun getTotalWithdrawnMoney(): Long

    @Query("select * from `your_transaction` order by id desc limit 1")
    fun getLastTransaction(): TransactionDetails

}
