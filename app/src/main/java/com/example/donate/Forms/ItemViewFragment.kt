package com.example.donate.Forms

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Adapter.MyAdapter
import com.example.donate.Models.UserViewModel

import com.example.donate.databinding.FragmentItemViewBinding

private lateinit var viewModel: UserViewModel
lateinit var adapter:MyAdapter

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
        binding.floatingActionButton2.setOnClickListener{
            val fr = requireFragmentManager().beginTransaction()
            fr.replace(com.example.donate.R.id.drawerLayout, AddItemFragment())
            fr.commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager=LinearLayoutManager(context)
        binding.recycler.setHasFixedSize(true)
        adapter= MyAdapter()
        binding.recycler.adapter= adapter
        viewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.allOrders.observe(viewLifecycleOwner, Observer {
            adapter.updateOrderList(it)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}