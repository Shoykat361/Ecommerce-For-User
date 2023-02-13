package com.example.ecommerceforuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceforuser.databinding.OrderRowLayoutBinding
import com.example.ecommerceforuser.databinding.ProductItemRowBinding
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.Order
import com.example.ecommerceforuser.models.Product
import com.example.ecommerceforuser.utlis.CartAction

class OrderAdapter (val callback :(String)->Unit):ListAdapter<Order,OrderAdapter.OrderViewHolder>(OrderDiffUtil()){

    class OrderViewHolder(val binding: OrderRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order=order
        }
    }

    class OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
        holder.itemView.setOnClickListener {
            callback(order.orderId!!)

        }
    }
}