package com.example.ecommerceforuser.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerceforuser.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:setImageUrl")
fun setImgUrl(imgView:ImageView,url :String?){
    url?.let {
        Glide.with(imgView.context)
            .load(url)
            .placeholder(R.drawable.img)
            .into(imgView)
    }

}
@BindingAdapter("app:setDate")
fun setDate(textView: TextView,timestamp: com.google.firebase.Timestamp){
   textView.text= SimpleDateFormat("dd/MM/yyyy hh:mm:a")
       .format(Date(timestamp.seconds*1000))

}