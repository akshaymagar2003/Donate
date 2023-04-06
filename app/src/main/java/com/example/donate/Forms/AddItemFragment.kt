package com.example.donate.Forms

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
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

var p: Int=10
class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!


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
        if (isEntryValid()) {
            addNewItem2(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),binding.itemCount.text.toString()
            )
        }
        val fr = requireFragmentManager().beginTransaction()
        fr.replace(com.example.donate.R.id.Framelayout, ItemViewFragment())
        fr.commit()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }







}
fun addNewItem2(itemName: String, itemPrice: String, itemCount: String) {

    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders")
    val order = Order(itemName, itemPrice, itemCount)
    databaseReference.child(p.toString()).setValue(order).addOnSuccessListener {
        Log.d(ContentValues.TAG, "New order added")
        p++
//        Toast.makeText(this,"Added Successfully",Toast.LENGTH_SHORT)
    }

}
fun isEntryValid2(itemName: String, itemPrice: String, itemCount: String): Boolean {
    if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
        return false
    }
    return true
}



