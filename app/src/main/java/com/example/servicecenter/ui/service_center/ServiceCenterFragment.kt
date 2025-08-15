package com.example.servicecenter.ui.service_center

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.servicecenter.databinding.FragmentServiceCenterBinding
import com.example.servicecenter.ui.service_center.Constants.SERVICE_CENTER_URL

class ServiceCenterFragment : Fragment() {

    private var _binding: FragmentServiceCenterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
        loadServiceCenter()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() = with(binding.serviceCenter) {
        settings.apply {
            javaScriptEnabled = true
            loadsImagesAutomatically = true
            /*
            useWideViewPort = true  // Responsive olmayan sayfalarda tüm sayfayı ekrana sığdırmak için kullanılabilir.
             */
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false
        }
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBarVisibility(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBarVisibility(false)
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                progressBarVisibility(false)
                binding.serviceCenter.loadData(
                    "<html><body><h2>Sayfa yüklenemedi!</h2><p>Lütfen internet bağlantınızı kontrol edin.</p></body></html>",
                    "text/html",
                    "UTF-8"
                )
            }
        }
    }

    private fun progressBarVisibility(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun loadServiceCenter() {
        binding.serviceCenter.loadUrl(SERVICE_CENTER_URL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
