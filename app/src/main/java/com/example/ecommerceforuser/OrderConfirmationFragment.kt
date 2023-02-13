package com.example.ecommerceforuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceforuser.ViewModels.OrderViewModel
import com.example.ecommerceforuser.ViewModels.UserViewModel
import com.example.ecommerceforuser.databinding.CartItemSimpleRowBinding
import com.example.ecommerceforuser.databinding.FragmentOrderConfirmationBinding
import com.example.ecommerceforuser.models.CartItem
import com.example.ecommerceforuser.models.OrderSetting
import com.example.ecommerceforuser.utlis.DbResult

class OrderConfirmationFragment : Fragment() {
    private lateinit var binding:FragmentOrderConfirmationBinding
    private val orderViewModel :OrderViewModel by activityViewModels()
    private val userViewModel :UserViewModel by activityViewModels()
    private var orderSettings:OrderSetting?=null
    private var cartList:List<CartItem> = mutableListOf()
    private var address :String?=null
    private var paymentMethod :String?=null


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)
        val bundle = arguments
        bundle?.let {
            address = it.getString("address")
            paymentMethod = it.getString("payment")
            binding.deliveryAddressTV.text = address
            binding.paymentMethodTV.text = paymentMethod
        }

        orderViewModel.getOrderSettings().observe(viewLifecycleOwner) {settings->
            userViewModel.getCartItems(userViewModel.getCurrentUserId()!!)
                .observe(viewLifecycleOwner) {cartList->
                    orderSettings=settings
                    this.cartList=cartList
                    createCartItemList(cartList, inflater)
                    binding.totalTV.text = "BDT${userViewModel.getCartTotalprice(cartList)}"
                    binding.deliveryChargeTV.text = "${settings.delivaryCharge}"
                    binding.discountLabelTV.text = "Discount(${settings.discount}%)"
                    binding.vatLabelTV.text = "VAT(${settings.vat}%)"
                    binding.discountAmountTV.text="BDT ${orderViewModel.getdisCountAmount(
                        total = userViewModel.getCartTotalprice(cartList),
                        settings=settings
                    )}"
                    binding.vatAmountTV.text=  "BDT ${orderViewModel.getVatCountAmount(
                        total = userViewModel.getCartTotalprice(cartList),
                        settings=settings
                    )}"
                    binding.grandTotalTV.text="BDT ${orderViewModel.getGrandtotal(
                        total = userViewModel.getCartTotalprice(cartList),
                        settings=settings
                    )}"
                }

        }
        binding.orderBtn.setOnClickListener {
            orderViewModel.addNewOrder(
                orderSettings!!,
                cartList,
                address,
                paymentMethod,
                orderViewModel.getGrandtotal(
                    total = userViewModel.getCartTotalprice(cartList),
                    settings=orderSettings!!
                ),
                userViewModel.getCurrentUserId()!!){
                if (it == DbResult.SUCCESS){
                    userViewModel.clearCart(cartList)
                    findNavController().navigate(R.id.action_orderConfirmationFragment_to_orderSuccessfulFragment)
                }
            }

        }
        return binding.root
    }
    private fun createCartItemList(it: List<CartItem>?, inflater: LayoutInflater) {
        it.let { cartList ->
            cartList!!.forEach { cartItem ->
                val cartBinding = CartItemSimpleRowBinding.inflate(inflater)
                cartBinding.item = cartItem
                binding.cartItemsLinearLayout.addView(cartBinding.root)
            }
        }
    }

}