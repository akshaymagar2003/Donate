package com.example.donate.Forms

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.example.donate.databinding.FragmentAddItemBinding
import com.example.donate.firebaseAdd.Operation


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
        return Operation.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString()
        )
    }
    private fun addNewItem() {
        if (isEntryValid()) {
            Operation.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),binding.itemCount.text.toString()
            )
        }
        val fr = requireFragmentManager().beginTransaction()
        fr.replace(com.example.donate.R.id.drawerLayout, ItemViewFragment())
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


