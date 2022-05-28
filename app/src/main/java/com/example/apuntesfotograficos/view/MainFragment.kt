package com.example.apuntesfotograficos.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentLoginBinding
import com.example.apuntesfotograficos.databinding.FragmentMainBinding
import com.example.apuntesfotograficos.utils.CameraUtil
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    var camUtil:CameraUtil? = null
    var uriImg:String? = null


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
        camUtil = CameraUtil(requireActivity())
        binding.cardApuntes.setOnClickListener { navController?.navigate(R.id.action_mainFragment_to_notesFragment) }
        binding.cardGrupos.setOnClickListener { Toast.makeText(context,"Grupos",Toast.LENGTH_LONG).show() }
        binding.addNote.setOnClickListener{ camUtil!!.captureImage(/*binding.vista*/) }
    }

    override public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        uriImg = camUtil?.uriNote(resultCode)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        if(!camUtil?.dirImg.isNullOrBlank())
            binding.vista.setImageURI(Uri.parse(camUtil!!.dirImg))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}