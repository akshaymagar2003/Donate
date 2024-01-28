package com.example.donate.Models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.donate.Repository.UserRepository

class UserViewModel(email :String ) : ViewModel(){
    private val repository : UserRepository
    private val _allOrders =MutableLiveData<List<Order>>()
    val allOrders : LiveData<List<Order>> = _allOrders

    init {
        val encodedEmail = email.replace(".", "_dot_")

        Log.d("testing","$encodedEmail")
        repository=UserRepository(encodedEmail)
        Log.d("testing","test6")
        repository.loadOrders(_allOrders)
    }
}