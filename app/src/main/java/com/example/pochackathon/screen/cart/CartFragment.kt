package com.example.pochackathon.screen.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pochackathon.R
import com.example.pochackathon.databinding.FragmentCartBinding
import com.example.pochackathon.model.Product
import com.example.pochackathon.preset.AppData

class CartFragment : Fragment(), CartController.Listener {
    private lateinit var binding: FragmentCartBinding
    private lateinit var controller: CartController
    private val cart get() = AppData.cart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        setupSummaryView()
        setupRecyclerView()
        setupOnClickView()
    }

    private fun setupSummaryView() {
        with(binding) {
            val allDiscountPrice = cart.sumOf { it.price.discount }
            val priceSummary = cart.sumOf { it.price.final }

            allDiscountPriceTextView.text = "ส่วนลด $allDiscountPrice บาท"
            priceSummaryTextView.text = "รวม $priceSummary บาท"
        }
    }

    private fun setupOnClickView() {
        binding.checkoutTextView.setOnClickListener {
            AppData.checkout()
            Toast.makeText(requireContext(), "ชำระเงินเรียบร้อย", Toast.LENGTH_SHORT).show()
            updateView()
        }
    }

    private fun setupRecyclerView() {
        controller = CartController().apply {
            setData(cart, this@CartFragment)
        }

        binding.recyclerView.setController(controller)
    }

    override fun removeProduct(product: Product) {
        AppData.removeProduct(product)
        updateView()
    }

    private fun updateView() {
        controller.setData(cart, this)
        setupSummaryView()
    }
}
