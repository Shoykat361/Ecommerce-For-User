package com.example.ecommerceforuser.models

data class UserofEcom(
    var userId :String?=null,
    var userName:String?=null,
    var emailAddress:String?=null,
    var userCreationTimeStamp:Long?=null,
    var userLastSignInTimeStamp:Long?=null,
    var phone:String?=null,
    var address:String?=null,
    var image:String?=null,
    var lastUsesTimestamp:Long?=null,
    var online: Boolean = false,
)
