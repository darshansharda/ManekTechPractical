package com.manektechpractical.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.manektechpractical.R
import com.manektechpractical.adapter.TransactionsAdapter
import com.manektechpractical.databinding.ActivityWithdrawMoneyBinding
import com.manektechpractical.roomdatabase.TransactionDetails
import com.manektechpractical.viewmodel.ViewModelFactory
import com.manektechpractical.viewmodel.WithdrawnMoneyViewModel

class WithdrawMoneyActivity : AppCompatActivity() {

    private val totalAmount: Long = 1000000
    private lateinit var binding: ActivityWithdrawMoneyBinding
    private val withdrawnMoneyViewModel by viewModels<WithdrawnMoneyViewModel>() {
        ViewModelFactory(application)
    }
    private val transactions = arrayListOf<TransactionDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw_money)
        binding.atmAmount = totalAmount
        setClickListener()
        setAdapter()
        setDataObservers()
    }

    private fun setAdapter() {
        binding.rvAllTransactions.apply {
            layoutManager = LinearLayoutManager(this@WithdrawMoneyActivity)
            adapter = TransactionsAdapter(this@WithdrawMoneyActivity, transactions)
        }
    }

    private fun setDataObservers() {

        withdrawnMoneyViewModel.apply {

            observeTransactionList().observe(this@WithdrawMoneyActivity) {
                if (it.isNotEmpty()) {
                    transactions.clear()
                    transactions.addAll(it)
                    binding.rvAllTransactions.adapter?.notifyDataSetChanged()
                    if (transactions.isNotEmpty())
                        binding.transaction = transactions[0]
                }
            }

            observerWithdrawnMoney().observe(this@WithdrawMoneyActivity) {
                binding.atmAmount = totalAmount - it
            }

        }
    }

    private fun setClickListener() {
        binding.btnWithDraw.setOnClickListener {
            if (isValidAmount()) {
                insertTransaction()
            }
        }
    }

    private fun insertTransaction() {
        val withdrawAmount = binding.etWithdrawAmount.text.toString().toInt()
        val note2000 = withdrawAmount / 2000
        val note500 = (withdrawAmount - (note2000 * 2000)) / 500
        val note200 = (withdrawAmount - ((note2000 * 2000) + (note500 * 500))) / 200
        val note100 =
            (withdrawAmount - ((note2000 * 2000) + (note500 * 500) + (note200 * 200))) / 100

        val transactionDetails = TransactionDetails(
            withDrawnAmount = withdrawAmount,
            note100 = note100,
            note200 = note200,
            note500 = note500,
            note2000 = note2000,
        )
        withdrawnMoneyViewModel.insertTransaction(transactionDetails)
        binding.etWithdrawAmount.setText("")
    }

    private fun isValidAmount(): Boolean {
        when {
            binding.etWithdrawAmount.text.toString().isEmpty() -> {
                Toast.makeText(this, getString(R.string.please_enter_amount), Toast.LENGTH_LONG)
                    .show()
                return false
            }
            binding.etWithdrawAmount.text.toString().toInt() == 0 -> {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_valid_amount),
                    Toast.LENGTH_LONG
                ).show()
                return false
            }
            binding.etWithdrawAmount.text.toString().toInt() % 100 != 0 -> {
                Toast.makeText(this, getString(R.string.multiple_of_100), Toast.LENGTH_LONG).show()
                return false
            }
            binding.etWithdrawAmount.text.toString().toInt() > binding.atmAmount?.toInt()!! -> {
                Toast.makeText(this, getString(R.string.not_have_balance), Toast.LENGTH_LONG).show()
                return false
            }

        }
        return true

    }

}