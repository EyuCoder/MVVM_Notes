package com.codexo.notes.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.codexo.notes.BuildConfig
import com.codexo.notes.R
import com.codexo.notes.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
    private var _binding: FragmentAboutBinding? = null
    private val binding
        get() = _binding
    private val verName = BuildConfig.VERSION_NAME
    private val verCode = BuildConfig.VERSION_CODE

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAboutBinding.bind(view)

        binding!!.apply {
            tvVerNum.text = "$verName ($verCode)"
            gitCard.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(gitUrl)
                startActivity(i)
            }
            btnRate.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(playStoreUrl)
                startActivity(i)
            }
        }
        setHasOptionsMenu(true)
    }

    companion object {
        const val gitUrl = "https://github.com/elysium09/MVVM_Notes"
        const val playStoreUrl = "https://play.google.com/store/apps/details?id=com.codexo.notes"
    }
}