package com.example.apuntesfotograficos.view

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentRegisterBinding
import com.example.apuntesfotograficos.interactor.DBInteractor
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.User
import com.example.apuntesfotograficos.presenter.DBPresenter


class RegisterFragment : Fragment(), IDatabase.View {
    var navController: NavController? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: DBPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        presenter = DBPresenter(DBInteractor())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = User()

        binding.btnRegistrar.setOnClickListener {
            user.user_name = binding.etName.text.toString()
            user.user_email = binding.etEmail.text.toString()
            user.user_password = binding.etRePassword.text.toString()
            if(validateFields()){
                presenter.registerUser(user, context)
                navController!!.navigate(R.id.action_registerFragment_to_loginFragment)
            } else
                Toast.makeText(context, "Debe llenar los campos",  Toast.LENGTH_LONG).show()
            }
        binding.btnBack.setOnClickListener { navController!!.navigate(R.id.action_registerFragment_to_loginFragment) }
    }

    private fun validateFields():Boolean{
        var name  = binding.etName.text.toString()
        var email  = binding.etEmail.text.toString()
        var passwd = binding.etRePassword.text.toString()
        var passwd2 = binding.etPassword.text.toString()
        var pattern = Patterns.EMAIL_ADDRESS
        return (passwd.equals(passwd2) && !name.isNullOrBlank() && pattern.matcher(email).matches())
    }

}