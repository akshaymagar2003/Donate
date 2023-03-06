package com.example.donate.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.donate.Repository.UserRepository

class UserViewModel : ViewModel(){
    private val repository : UserRepository
    private val _allOrders =MutableLiveData<List<Order>>()
    val allOrders : LiveData<List<Order>> = _allOrders

    init {
        repository=UserRepository().getInstance()
        repository.loadOrders(_allOrders)
    }
}