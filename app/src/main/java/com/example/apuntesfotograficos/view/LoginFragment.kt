package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.example.apuntesfotograficos.interactor.DBInteractor
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.User
import com.example.apuntesfotograficos.presenter.DBPresenter

class LoginFragment : Fragment(), IDatabase.View {
    var navController: NavController? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: DBPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        presenter = DBPresenter(DBInteractor())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = User()

        binding.btnLoginRegistrar.setOnClickListener { navController!!.navigate(R.id.action_loginFragment_to_registerFragment) }
        binding.btnEntrar.setOnClickListener {
            user.user_email = binding.etCorreo.text.toString()
            user.user_password = binding.etPassword.text.toString()
            if(validateFields()){
                val preferencias  = requireActivity().
                getSharedPreferences("userData", Context.MODE_PRIVATE)
                var userData = presenter.initSession(user,context)
                if(userData != null){
                    Log.e("onViewCreated","${userData?.user_id}  - ${userData?.user_name} - ${userData?.user_email}")
                    val editor = preferencias.edit()
                    editor.putInt("id", userData.user_id!!)
                    editor.putString("name", userData.user_name)
                    editor.putString("email", userData.user_email)
                    editor.commit()
                    navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }else
                Toast.makeText(context, "Ingrese su correo y contraseña",  Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields():Boolean{
        var email  = binding.etCorreo.text.toString()
        var password  = binding.etPassword.text.toString()
        var pattern = Patterns.EMAIL_ADDRESS
        return (!password.isNullOrBlank() && pattern.matcher(email).matches())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}