package com.example.donate.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.donate.Models.UserViewModel

class UserViewModelFactory(private val argumentValue: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(argumentValue) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}