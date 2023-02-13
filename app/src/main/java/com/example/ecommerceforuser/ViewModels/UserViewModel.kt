package com.example.ecommerceforuser.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.UserofEcom
import com.example.ecommerceforuser.repos.UserRepository
import com.google.firebase.auth.FirebaseAuth

class UserViewModel :ViewModel() {
    val userRepository = UserRepository()
    fun updateUser(ecomUser: UserofEcom)=userRepository.updateUser(ecomUser)
    fun getCurrentUserId()=FirebaseAuth.getInstance().currentUser?.uid
    fun getUser(userId: String)=userRepository.getUser(userId)
    fun getCartItems(userId: String) = userRepository.getAllCartItems(userId)
    fun addToCart(userId: String, cartItem: CartItem) = userRepository.addToCart(userId, cartItem)
    fun removeFromCart(userId: String, productId: String) = userRepository.removeFromCart(userId, productId)
    fun updateCartItem(userId: String,productId: String,qty :Int)=userRepository.updateCartItem(userId,productId,qty)
    fun getCartTotalprice(it: List<CartItem>?):Double {
        var total = 0.0
        it?.let {
            it.forEach {
                total += it.quantity * it.price!!
            }
        }
        return total
    }
    fun clearCart(cartList: List<CartItem>)=userRepository.clearCart(getCurrentUserId()!!,cartList)
}