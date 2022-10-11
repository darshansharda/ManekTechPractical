package com.manektechpractical.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.manektechpractical.R
import com.manektechpractical.databinding.RowTransactionBinding
import com.manektechpractical.roomdatabase.TransactionDetails

class TransactionsAdapter(
    private val context: Context,
    private val transactions: List<TransactionDetails>
) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {
    class TransactionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: RowTransactionBinding? = DataBindingUtil.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionsHolder(
        LayoutInflater.from(context).inflate(
            R.layout.row_transaction, parent, false
        )
    )

    override fun onBindViewHolder(holder: TransactionsHolder, position: Int) {

        holder.binding?.apply {
            pos = position
            transaction = transactions[holder.adapterPosition]
        }

    }

    override fun getItemCount() = transactions.size
}