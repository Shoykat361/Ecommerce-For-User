package com.example.ecommerceforuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceforuser.databinding.ProductItemRowBinding
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.Product
import com.example.ecommerceforuser.utlis.CartAction

class ProductAdapter(val cartBtnAction: (String, CartItem) -> Unit) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtil()){

    class ProductViewHolder(val binding: ProductItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
        }
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        if (product.inCart) {
            holder.binding.productRowCartBtn.text = CartAction.removeFromCart
        } else {
            holder.binding.productRowCartBtn.text = CartAction.addToCart
        }
        holder.bind(product)
        holder.itemView.setOnClickListener {

        }
        holder.binding.productRowCartBtn.setOnClickListener {
            val cartItem = CartItem(
                productId = product.id,
                productName = product.name,
                price = product.salesPrice
            )
            val action = if (product.inCart) CartAction.removeFromCart else CartAction.addToCart
            cartBtnAction(action, cartItem)
        }
    }
}