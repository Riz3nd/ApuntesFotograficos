package com.example.apuntesfotograficos.view

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentRegisterBinding
import com.example.apuntesfotograficos.interactor.DBInteractor
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.User
import com.example.apuntesfotograficos.presenter.DBPresenter
import kotlinx.coroutines.launch


class RegisterFragment : Fragment(), IDatabase.View {
    var navController: NavController? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: DBPresenter
    var userDao = MainActivity.dbRoom?.userDao()

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
        binding.btnRegistrar.setOnClickListener {
            if(validateFields()){
//                presenter.registerUser(user, context)
                lifecycleScope.launch {
                    userDao?.insertUser(User(
                        0,
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etRePassword.text.toString()
                    ))
                }
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
        return ((passwd.length > 6 && passwd2.length > 6) && (passwd.equals(passwd2) &&
                !name.isNullOrBlank()) && pattern.matcher(email).matches())
    }

}