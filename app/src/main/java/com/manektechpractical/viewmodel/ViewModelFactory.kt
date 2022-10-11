package com.manektechpractical.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(var appClass: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WithdrawnMoneyViewModel::class.java)) {
            return WithdrawnMoneyViewModel(appClass) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }

}