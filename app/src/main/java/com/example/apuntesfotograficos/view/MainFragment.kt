package com.example.apuntesfotograficos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentLoginBinding
import com.example.apuntesfotograficos.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = findNavController()
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardApuntes.setOnClickListener { Toast.makeText(context,"Apuntes",Toast.LENGTH_LONG).show() }
        binding.cardGrupos.setOnClickListener { Toast.makeText(context,"Grupos",Toast.LENGTH_LONG).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}