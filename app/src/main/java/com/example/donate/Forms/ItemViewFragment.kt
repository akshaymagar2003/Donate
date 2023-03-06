package com.example.donate.Forms

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.donate.databinding.FragmentItemViewBinding


class ItemViewFragment : Fragment() {
    private var _binding: FragmentItemViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemViewBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.floatingActionButton.setOnClickListener{
            val fr = requireFragmentManager().beginTransaction()
            fr.replace(com.example.donate.R.id.drawerLayout, AddItemFragment())
            fr.commit()
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}