package com.example.donate.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.donate.Models.Order
import com.google.firebase.database.*

class UserRepository (val email: String){

    private val databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference("orders").child(email)

//    companion object {
//        @Volatile
//        private var INSTANCE: UserRepository? = null
//
////        fun getInstance(email: String): UserRepository {
////            return INSTANCE ?: synchronized(this) {
////                val instance = UserRepository(email)
////                Log.d("testing7", "$email")
////                INSTANCE = instance
////                instance
////            }
////        }
//    }

    fun loadOrders(orderlist:MutableLiveData<List<Order>>){
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                          val _userlist: List<Order> =snapshot.children.map{
                              dataSnapshot ->dataSnapshot.getValue(Order::class.java) !!
                          }

                    orderlist.postValue(_userlist)
                }catch( e:Exception ){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }


}