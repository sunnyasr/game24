package com.mycondo.a99hub24.ui.rules

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.databinding.FragmentRulesBinding


class RulesFragment : Fragment(R.layout.fragment_rules) {

    protected lateinit var binding: FragmentRulesBinding
    private lateinit var mWebView: WebView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRulesBinding.bind(view)
        mWebView = binding.webview
        mWebView.loadUrl("file:///android_asset/rules.html");
        mWebView.settings.javaScriptEnabled = true

    }


}