package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentUserBinding


class UserFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferencias  = requireActivity().
        getSharedPreferences("userData", Context.MODE_PRIVATE)
        binding.tvUserName.text = "${preferencias.getString("name", "")}"
        binding.tvUserEmail.text = "Correo: ${preferencias.getString("email", "")}"
        binding.btnSignOut.setOnClickListener { navController?.navigate(R.id.action_userFragment_to_loginFragment) }
        binding.btnBack.setOnClickListener { navController?.navigate(R.id.action_userFragment_to_mainFragment) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}