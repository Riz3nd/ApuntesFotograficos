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

class LoginFragment : Fragment() {
    var navController: NavController? = null
    lateinit var btn_login_registrar: Button
    lateinit var mView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        mView =  inflater.inflate(R.layout.fragment_login, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login_registrar = mView.findViewById(R.id.btn_login_registrar)
        btn_login_registrar!!.setOnClickListener { navController!!.navigate(R.id.action_loginFragment_to_registerFragment) }
    }

}