package com.example.pochackathon.screen.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.pochackathon.databinding.FragmentScannerBinding
import com.example.pochackathon.preset.AppData
import com.example.pochackathon.source.Api
import com.google.zxing.Result
import kotlinx.coroutines.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler {
    private lateinit var binding: FragmentScannerBinding
    private var scannerView: ZXingScannerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(layoutInflater)
        scannerView = ZXingScannerView(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setAspectTolerance(0.5f)
        }
        binding.scannerView.addView(scannerView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        setupButton()
        startScannerView()
    }

    private fun startScannerView() {
        scannerView?.setResultHandler(this@ScannerFragment)
        scannerView?.startCamera()
    }

    override fun onResume() {
        super.onResume()

        startScannerView()
    }

    override fun onPause() {
        super.onPause()

        scannerView?.stopCamera()
    }

    private fun setupButton() {
        binding.addToCartButton.setOnClickListener { onClickAddToCart() }
    }

    private fun onClickAddToCart() {
        binding.addToCartButton.isEnabled = false

        val barcode = binding.barcodeEditText.text?.toString() ?: return
        val sku = getSku(barcode) ?: return

        addProductToCart(sku)
    }

    private fun getSku(barcode: String): String? {
        return AppData.getSku(requireContext(), barcode)
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
            handleAddedToCart()
        }
    }

    private fun handleAddedToCart() {
        with(binding) {
            addToCartButton.isEnabled = true
            barcodeEditText.text.clear()

            startScannerView()
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun handleResult(result: Result?) {
        val barcode = result?.text ?: return

        binding.barcodeEditText.setText(barcode)
        binding.addToCartButton.performClick()
    }
}
