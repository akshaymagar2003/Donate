package com.example.donate.Repository

import androidx.lifecycle.MutableLiveData
import com.example.donate.Models.Order
import com.google.firebase.database.*

class UserRepository {
    private val databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference("orders")

   @Volatile private var INSTANCE: UserRepository ?=null
    fun getInstance():UserRepository{
        return  INSTANCE ?: synchronized(this){
            val instance=UserRepository()
            INSTANCE=instance
            instance
        }
    }

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