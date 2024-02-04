package com.example.donate.Forms

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.donate.Models.Order

import com.example.donate.databinding.FragmentAddItemBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random


class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
//    private val sharedPref2=requireActivity().getPreferences(Context.MODE_PRIVATE)
  var p: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            _binding = FragmentAddItemBinding.inflate(inflater, container, false)
            return binding.root
    }

    private fun isEntryValid(): Boolean {
        return isEntryValid2(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString()
        )
    }
    private fun addNewItem() {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val value = preferences.getString("Email", "error")
        Log.d("helping","$value")
        if (isEntryValid()) {
            addNewItem2(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),binding.itemCount.text.toString(),value.toString()
            )
        }
        val fr = requireFragmentManager().beginTransaction()
        fr.replace(com.example.donate.R.id.frame_layout, ItemViewFragment() )
        fr.commit()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
//        val sharedPref2=requireActivity().getPreferences(Context.MODE_PRIVATE)?:return
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }



    fun setMaxOrderId(value:String,p:Int){
//    val encodedEmail = value.replace(".", "_dot_")
//    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders").child(encodedEmail)
//    databaseReference.child("maxorderid").setValue(p)
        val sharedPref=requireActivity().getPreferences(Context.MODE_PRIVATE)?:return

        with(sharedPref.edit())
        {
            putString("max$value",p.toString())
            apply()
        }

    }
    fun getMaxOrderId(value:String):Int{
//    val encodedEmail = value.replace(".", "_dot_")
//    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders").child(encodedEmail)
//    databaseReference.child("maxorderid").setValue(p)

//        val p=sharedPref2.getString("max$value","10")
        if (p != null) {
            return p.toInt()
        }
        return 0
    }


    fun addNewItem2(itemName: String, itemPrice: String, itemCount: String,value:String) {
        val encodedEmail = value.replace(".", "_dot_")
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders").child(encodedEmail)
        val order = Order(itemName, itemPrice, itemCount)
        val sharedPref2=requireActivity().getPreferences(Context.MODE_PRIVATE)
        val pq=sharedPref2.getString("max$value","10")
        if (pq != null) {
          p=pq.toInt()
        }else{
            p=0
        }

        databaseReference.child(p.toString()).setValue(order).addOnSuccessListener {
            Log.d(ContentValues.TAG, "New order added")
            p++
//        Toast.makeText(,"Added Successfully",Toast.LENGTH_SHORT)
            Log.d("hollow", "$p")
            with(sharedPref2.edit())
            {
                putString("max$value", p.toString())
                apply()

            }
        }
    }
    fun isEntryValid2(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }
}







