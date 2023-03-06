package com.example.donate.firebaseAdd

class Operation {



    companion object {
        fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
            if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
                return false
            }
            return true
        }

        fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {

        }
    }
}