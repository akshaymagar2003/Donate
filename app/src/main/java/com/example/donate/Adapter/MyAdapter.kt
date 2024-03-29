package com.example.donate.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Models.Order
import com.example.donate.R

class MyAdapter(private val onItemClick: (Order) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val orderList = ArrayList<Order>()
//  var onItemClick: ((Order)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView =LayoutInflater.from(parent.context).inflate(
           R.layout.user_order,
           parent,false
       )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val currentorder = orderList[position]
        holder.name.text=currentorder.TO
        holder.price.text=currentorder.Food_Name
        holder.quantity.text=currentorder.quantity

        holder.itemView.setOnClickListener {
            onItemClick(currentorder)

        }
    }
    fun updateOrderList(orderList: List<Order>){

           this.orderList.clear()

        this.orderList.addAll(orderList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val name: TextView=itemView.findViewById(R.id.name)
        val price: TextView=itemView.findViewById(R.id.price)
        val quantity: TextView=itemView.findViewById(R.id.quantity)


    }

}