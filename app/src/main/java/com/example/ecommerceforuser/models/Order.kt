package com.example.ecommerceforuser.models

import com.example.ecommerceforuser.utlis.OrderStatus
import com.example.ecommerceforuser.utlis.PaymentMethod
import com.google.firebase.Timestamp

data class Order(
    var orderId:String?=null,
    var userId:String?=null,
    var orderDate:Timestamp?=null,
    var delivaryCharge:Int?=0,
    var discount:Int?=0,
    var vat:Int?=0,
    var grandtotal :Double?=null,
    var delivery_address:String?=null,
    var orderStatus:String= OrderStatus.pending,
    var paymentMethod:String= PaymentMethod.cod,

    )
