package com.example.donate.Forms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.example.donate.Models.Order
import com.example.donate.R
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.*


class Item_Detail : Fragment() {

  private var Item: Order?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_item__detail, container, false)
      val  textView:TextView=view.findViewById(R.id.item_name)
        val  textView2:TextView=view.findViewById(R.id.item_price)
        val  textView3:TextView=view.findViewById(R.id.item_count)

        val args=this.arguments
        val inputData= args?.getString("data")
        Item =Gson().fromJson(inputData,Order::class.java)
       Item?.let {
           textView.setText(it.TO)
           textView2.setText(it.Food_Name)

           textView3.setText(it.quantity)
       }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
    fun onBackPressed() {
     val fragment:Fragment=ItemViewFragment()
        val fr = requireFragmentManager().beginTransaction()
        var replace = fr.replace(com.example.donate.R.id.Item_Detail, fragment)
        fr.commit()

    }

}