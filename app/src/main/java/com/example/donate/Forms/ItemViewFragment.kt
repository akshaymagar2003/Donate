package com.example.donate.Forms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.Adapter.MyAdapter
import com.example.donate.Models.Order
import com.example.donate.Models.UserViewModel
import com.example.donate.Repository.UserViewModelFactory

import com.example.donate.databinding.FragmentItemViewBinding
import com.google.gson.Gson

private lateinit var viewModel: UserViewModel
lateinit var adapter:MyAdapter

class ItemViewFragment : Fragment() {
    private var _binding: FragmentItemViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemViewBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.floatingActionButton2.setOnClickListener{
            val fr = requireFragmentManager().beginTransaction()
            fr.replace(com.example.donate.R.id.Framelayout, AddItemFragment())
            fr.commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager=LinearLayoutManager(context)
        binding.recycler.setHasFixedSize(true)
        adapter= MyAdapter(::onItemClicked)
        binding.recycler.adapter= adapter
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val value = preferences.getString("Email", "error")
        Log.d("testing","$value")
        viewModel= ViewModelProvider(this, UserViewModelFactory(value.toString())).get(UserViewModel::class.java)
        viewModel.allOrders.observe(viewLifecycleOwner, Observer {
            adapter.updateOrderList(it)
        })


//        adapter.onItemClick={
//            val bundle=Bundle()
////            bundle.putString("data", Gson().toJson(Order))
//            val fragment=Item_Detail()
//            fragment.arguments=bundle
//            val fr = requireFragmentManager().beginTransaction()
//            fr.replace(com.example.donate.R.id.drawerLayout, fragment)
//            fr.commit()
//        }
    }
    private fun onItemClicked(item:Order){
        val bundle=Bundle()
        bundle.putString("data", Gson().toJson(item))
            val fragment=Item_Detail()
            fragment.arguments=bundle
            val fr = requireFragmentManager().beginTransaction()
            fr.replace(com.example.donate.R.id.Framelayout, fragment,null).addToBackStack(null)
            fr.commit()
//                  Toast.makeText(requireContext(), "${item.name}", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}