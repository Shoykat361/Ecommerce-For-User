package com.example.ecommerceforuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceforuser.ViewModels.UserViewModel
import com.example.ecommerceforuser.databinding.FragmentCheckOutBinding
import com.example.ecommerceforuser.models.UserofEcom
import com.example.ecommerceforuser.utlis.PaymentMethod

class CheckOutFragment : Fragment() {
    private lateinit var binding:FragmentCheckOutBinding
    private var paymentMethod = PaymentMethod.cod
    private var deliveryAddress: String? = null
    private var ecomUser: UserofEcom? = null
    private val userViewModel : UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        binding.paymentRG.setOnCheckedChangeListener { group, checkedId ->
            val rb: RadioButton = container!!.findViewById(checkedId)
            paymentMethod = rb.text.toString()
        }
        userViewModel.getUser(userViewModel.getCurrentUserId()!!)
            .observe(viewLifecycleOwner) {
                ecomUser = it
                it.address?.let { address ->
                    deliveryAddress = address
                    binding.deliveryAddressET.setText(address)
                }
            }
        binding.nextButton.setOnClickListener {
            val address = binding.deliveryAddressET.text.toString()
           ecomUser?.let { user ->
                user.address = address
                userViewModel.updateUser(user)
                findNavController()
                    .navigate(R.id.action_checkOutFragment_to_orderConfirmationFragment,args = bundleOf("address" to address, "payment" to paymentMethod))

            }
        }
        return binding.root
    }
}