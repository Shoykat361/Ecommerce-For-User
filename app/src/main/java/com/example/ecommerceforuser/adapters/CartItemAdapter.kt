package com.example.ecommerceforuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceforuser.databinding.CartItemLayoutBinding
import com.example.ecommerceforuser.models.CartItem


class CartItemAdapter(val cartBtnAction: (CartItem) -> Unit) : ListAdapter<CartItem, CartItemAdapter.CartItemViewHolder>(CartItemDiffUtil()){

    class CartItemViewHolder(val binding: CartItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(cartItem: CartItem) {
                    binding.item = cartItem
                }
            }

    class CartItemDiffUtil : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = CartItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)
        updateInfo(cartItem,holder)
        holder.binding.cartplusImgView.setOnClickListener {
            cartItem.quantity += 1
            updateInfo(cartItem,holder)
            cartBtnAction(cartItem)
        }
        holder.binding.cartminusImgView.setOnClickListener {
            if (cartItem.quantity >1){
                cartItem.quantity -=1
                updateInfo(cartItem,holder)
                cartBtnAction(cartItem)
            }

        }

       /* holder.binding.cartPlusBtnIV.setOnClickListener {
            cartItem.quantity += 1
            updateInfo(cartItem, holder)
            cartBtnAction(cartItem)
        }

        holder.binding.cartMinusBtnIV.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity -= 1
                updateInfo(cartItem, holder)
                cartBtnAction(cartItem)
            }
        }*/

    }
    private fun updateInfo(cartItem: CartItem, holder: CartItemViewHolder){
        val pricewithQty=cartItem.quantity * cartItem.price!!
        holder.binding.quantity=cartItem.quantity
        holder.binding.price=pricewithQty

    }


}