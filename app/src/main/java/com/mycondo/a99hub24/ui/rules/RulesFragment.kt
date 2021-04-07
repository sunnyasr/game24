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


class RulesFragment : Fragment() {

    protected lateinit var binding: FragmentRulesBinding
    private lateinit var mWebView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mWebView = binding.webview
        mWebView.loadUrl("file:///android_asset/rules.html");
        mWebView.settings.javaScriptEnabled = true

    }


}