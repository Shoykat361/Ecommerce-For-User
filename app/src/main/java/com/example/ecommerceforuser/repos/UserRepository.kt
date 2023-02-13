package com.example.ecommerceforuser.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.UserofEcom
import com.example.ecommerceforuser.utlis.collectionCart
import com.example.ecommerceforuser.utlis.collectionUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    val db = FirebaseFirestore.getInstance()

    fun insertNewUser(ecomUser: UserofEcom) {
        db.collection(collectionUser).document(ecomUser.userId!!)
            .set(ecomUser)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }
    fun getUser(userId: String):LiveData<UserofEcom>{
        val userLD= MutableLiveData<UserofEcom>()
        db.collection(collectionUser)
            .document(userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                userLD.value = value!!.toObject(UserofEcom::class.java)

            }
        return userLD

    }
    fun updateUser(ecom: UserofEcom) {
        db.collection(collectionUser).document(ecom.userId!!)
            .set(ecom)

            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun addToCart(userId: String, cartItem: CartItem) {
        val cartDocRef = db.collection(collectionUser).document(userId)
            .collection(collectionCart).document(cartItem.productId!!)
        cartDocRef.set(cartItem)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun removeFromCart(userId: String, productId: String) {
        db.collection(collectionUser).document(userId)
            .collection(collectionCart).document(productId).delete()
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }
    fun updateCartItem(userId: String,productId: String,qty :Int){
        db.collection(collectionUser).document(userId)
            .collection(collectionCart).document(productId)
            .update("quantity",qty)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }
    fun clearCart(userId: String,cartList: List<CartItem>){
        val wb = db.batch()
        cartList.forEach {
          val doc=   db.collection(collectionUser).document(userId)
                .collection(collectionCart).document(it.productId!!)
            wb.delete(doc)

        }
        wb.commit()
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }

    }

    fun getAllCartItems(userId: String) : LiveData<List<CartItem>> {
        val cartLD = MutableLiveData<List<CartItem>>()
        db.collection(collectionUser).document(userId)
            .collection(collectionCart)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val tempList = mutableListOf<CartItem>()
                for (doc in value!!.documents) {
                    doc.toObject(CartItem::class.java)?.let { tempList.add(it) }
                }
                cartLD.value = tempList
            }
        return cartLD
    }

    fun updateLastSignInTimeAndOnlineStatus(userId: String, time: Long) {
        db.collection(collectionUser).document(userId)
            .update(mapOf("userLastSignInTimeStamp" to time, "online" to true))
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun updateLastAppExitTimeAndOnlineStatus(time: Long, userId: String, status: Boolean, callback: (() -> Unit)? = null) {
        db.collection(collectionUser).document(userId)
            .update(mapOf("lastUsageTimestamp" to time, "online" to status))
            .addOnSuccessListener {
                callback?.invoke()
            }.addOnFailureListener {

            }
    }

    fun updateOnlineStatus(userId: String, status: Boolean, callback: (() -> Unit)? = null) {
        db.collection(collectionUser).document(userId)
            .update("online", status)
            .addOnSuccessListener {
                callback?.let { it1 -> it1() }
            }.addOnFailureListener {

            }
    }
}