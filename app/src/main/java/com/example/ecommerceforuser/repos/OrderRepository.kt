package com.example.ecommerceforuser.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceforuser.models.Order
import com.example.ecommerceforuser.models.OrderDetails
import com.example.ecommerceforuser.models.OrderSetting
import com.example.ecommerceforuser.utlis.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class OrderRepository {
    val db= FirebaseFirestore.getInstance()
    fun addNewOrder(order: Order,orderDetailsList: List<OrderDetails>,callback:(DbResult)->Unit){
        val wb=db.batch()
        val orderDoc=db.collection(collectionOrder).document()
        order.orderId=orderDoc.id
        wb.set(orderDoc,order)
        orderDetailsList.forEach {
            val detailsDoc=db.collection(collectionOrder)
                .document(order.orderId!!)
                .collection(collectionOrderDetails)
                .document(it.productId!!)
            wb.set(detailsDoc,it)
        }
        wb.commit()
            .addOnSuccessListener {
                callback(DbResult.SUCCESS)

            }
            .addOnFailureListener {
                callback(DbResult.FAILURE)


            }

    }
    fun getOrderSettings():LiveData<OrderSetting>{
        val settingsLD = MutableLiveData<OrderSetting>()
        db.collection(collectionOrderSettings).document(documentOrderConstants)
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                settingsLD.value=value!!.toObject(OrderSetting::class.java)
            }
        return settingsLD

    }
    fun getOrderbyUser(userId:String):LiveData<List<Order>>{
        val orderLD = MutableLiveData<List<Order>>()
        db.collection(collectionOrder)
            .whereEqualTo("userId",userId)
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Order>()
                for (doc in value!!.documents){
                    doc.toObject(Order::class.java)?.let { tempList.add(it) }

                }
                orderLD.value=tempList

            }
        return orderLD

    }
}