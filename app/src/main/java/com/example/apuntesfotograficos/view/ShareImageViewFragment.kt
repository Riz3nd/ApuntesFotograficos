package com.example.apuntesfotograficos.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentShareImageViewBinding

class ShareImageViewFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentShareImageViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentShareImageViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var srcImage = arguments?.getString("src_image") ?: "No disponible"
        binding.imageContent.setImageURI(Uri.parse("file://${srcImage}"))
        binding.closeImage.setOnClickListener {
            navController?.navigate(R.id.action_shareImageViewFragment_to_shareImagesFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}