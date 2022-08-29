package com.example.pochackathon.screen.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pochackathon.databinding.FragmentScannerBinding
import com.example.pochackathon.preset.AppData
import com.example.pochackathon.source.Api
import kotlinx.coroutines.*

class ScannerFragment : Fragment() {
    private lateinit var binding: FragmentScannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        setupButton()
    }

    private fun setupButton() {
        binding.addToCartButton.setOnClickListener { onClickAddToCart() }
    }

    private fun onClickAddToCart() {
        binding.addToCartButton.isEnabled = false

        val barcode = binding.barcodeEditText.text?.toString() ?: return
        val sku = AppData.getSku(requireContext(), barcode) ?: return

        addProductToCart(sku)
    }

    private fun addProductToCart(sku: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val product = Api.getProduct(sku)
                AppData.addProduct(product)
                toast("เพิ่มสินค้าลงในตะกร้าแล้ว")
            } catch (e: Throwable) {
                toast("ไม่พบสินค้า กรุณาลองใหม่")
            }
        }.invokeOnCompletion {
            cleanAndEnabledButton()
        }
    }

    private fun cleanAndEnabledButton() {
        with(binding) {
            barcodeEditText.text.clear()
            addToCartButton.isEnabled = true
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
