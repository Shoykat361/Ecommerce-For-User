package com.example.ecommerceforuser.ViewModels

import android.location.Address
import androidx.lifecycle.ViewModel
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.Order
import com.example.ecommerceforuser.models.OrderDetails
import com.example.ecommerceforuser.models.OrderSetting
import com.example.ecommerceforuser.repos.OrderRepository
import com.example.ecommerceforuser.utlis.DbResult
import com.example.ecommerceforuser.utlis.PaymentMethod
import com.google.firebase.Timestamp

class OrderViewModel:ViewModel() {
    private val orderRepository = OrderRepository()
    fun getOrderSettings()=orderRepository.getOrderSettings()

    fun getOrderyUser(userId:String) = orderRepository.getOrderbyUser(userId)

    fun getdisCountAmount(total:Double,settings: OrderSetting):Double{
        return (total*settings.discount)/100
    }
    fun getVatCountAmount(total:Double,settings: OrderSetting):Double{
        val PriceAfterDiscount=total - getdisCountAmount(total, settings)

        return (PriceAfterDiscount*settings.vat)/100
    }
    fun  getGrandtotal(total: Double,settings: OrderSetting):Double{
        val PriceAfterDiscount=total - getdisCountAmount(total, settings)
        return PriceAfterDiscount+getVatCountAmount(total, settings)+settings.delivaryCharge

    }
    fun addNewOrder(settings: OrderSetting,
                    cartList: List<CartItem>,
                    address: String?,
                    paymentMethod: String?,
                    grandTotal:Double,
                    currentUserId:String,
                    callback:(DbResult)->Unit){
        val orderModel =Order(
            userId = currentUserId,
            orderDate = Timestamp.now(),
            delivery_address =address,
            paymentMethod = paymentMethod!!,
            delivaryCharge = settings.delivaryCharge,
            discount = settings.discount,
            vat = settings.vat,
            grandtotal = grandTotal

        )
        val  orderDetailsList= mutableListOf<OrderDetails>()
        cartList.forEach {
            orderDetailsList.add(OrderDetails(
                productId = it.productId,
                productName = it.productName,
                productPrice = it.price!!,
                productQuantity = it.quantity,

            ))

        }
        orderRepository.addNewOrder(
            order = orderModel,
            orderDetailsList=orderDetailsList,
            callback=callback,
        )


    }
}