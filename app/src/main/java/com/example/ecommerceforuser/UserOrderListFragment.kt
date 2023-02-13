package com.example.ecommerceforuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceforuser.ViewModels.OrderViewModel
import com.example.ecommerceforuser.ViewModels.UserViewModel
import com.example.ecommerceforuser.adapters.OrderAdapter
import com.example.ecommerceforuser.databinding.FragmentUserOrderListBinding

class UserOrderListFragment : Fragment() {
    private lateinit var binding:FragmentUserOrderListBinding
    private val orderViewModel : OrderViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserOrderListBinding.inflate(inflater,container,false)
        val adapter =OrderAdapter(){

        }
        binding.orderRV.layoutManager=LinearLayoutManager(requireActivity())
        binding.orderRV.adapter=adapter
        orderViewModel.getOrderyUser(userViewModel.getCurrentUserId()!!)
            .observe(viewLifecycleOwner){
                adapter.submitList(it)

            }

        return binding.root
    }
}