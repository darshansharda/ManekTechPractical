package com.manektechpractical.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manektechpractical.roomdatabase.TransactionDatabase
import com.manektechpractical.roomdatabase.TransactionDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WithdrawnMoneyViewModel(appClass: Application) : AndroidViewModel(appClass) {

    private val transactionList = MutableLiveData<List<TransactionDetails>>()
    private val remainingMoney = MutableLiveData<Long>()
    private val database = TransactionDatabase.getDatabaseInstance(appClass.applicationContext)
    private val transactionDao = database?.getTransactionDao()

    init {
        getAllTransaction()
    }

    fun insertTransaction(transaction: TransactionDetails) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                transactionDao?.insertTransaction(transaction)
            }
            getAllTransaction()
        }

    }

    private fun getAllTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = transactionDao?.getAllTransaction()
            transactionList.postValue(list)
        }
        getWithdrawnMoney()
    }


    private fun getWithdrawnMoney() {
        viewModelScope.launch(Dispatchers.IO) {
            val totalWithdrawnMoney = transactionDao?.getTotalWithdrawnMoney()
            remainingMoney.postValue(totalWithdrawnMoney)
        }
    }

    fun observeTransactionList(): LiveData<List<TransactionDetails>> = transactionList

    fun observerWithdrawnMoney(): LiveData<Long> = remainingMoney


}