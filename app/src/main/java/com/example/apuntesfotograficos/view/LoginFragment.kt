package com.example.apuntesfotograficos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoginRegistrar.setOnClickListener { navController!!.navigate(R.id.action_loginFragment_to_registerFragment) }
        binding.btnEntrar.setOnClickListener { navController!!.navigate(R.id.action_loginFragment_to_mainFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}