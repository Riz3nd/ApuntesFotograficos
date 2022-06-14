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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.NoteApp
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentLoginBinding
import com.example.apuntesfotograficos.interactor.DBInteractor
import com.example.apuntesfotograficos.interfaces.IDatabase
import com.example.apuntesfotograficos.model.User
import com.example.apuntesfotograficos.presenter.DBPresenter
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), IDatabase.View {
    var navController: NavController? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var userDao = MainActivity.dbRoom?.userDao()
//    private lateinit var presenter: DBPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        presenter = DBPresenter(DBInteractor())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoginRegistrar.setOnClickListener { navController!!.navigate(R.id.action_loginFragment_to_registerFragment) }
        binding.btnEntrar.setOnClickListener {
            if(validateFields()){
                val preferencias  = requireActivity().
                getSharedPreferences("userData", Context.MODE_PRIVATE)
                lifecycleScope.launch {
                    var userData = userDao?.sessionUser(
                        binding.etCorreo.text.toString(),
                        binding.etPassword.text.toString())
                    if(userData != null){
                        Log.e("onViewCreated 2","${userData?.user_id}  - ${userData?.user_name} - ${userData?.user_email}")
                        val editor = preferencias.edit()
                        editor.putInt("id", userData.user_id!!)
                        editor.putString("name", userData.user_name)
                        editor.putString("email", userData.user_email)
                        editor.commit()
                        navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
                    }
                }
            }else{
                Toast.makeText(context, "Credenciales inválidas",  Toast.LENGTH_LONG).show()
            }
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