package com.example.apuntesfotograficos.view

import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentMainBinding
import com.example.apuntesfotograficos.utils.CameraUtil
import com.example.apuntesfotograficos.utils.CommonUtils
import com.example.apuntesfotograficos.utils.Constans
import com.example.apuntesfotograficos.utils.ImageAdapter
import java.io.File

class MainFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    var camUtil:CameraUtil? = null
    var imagenes:MutableList<String>? = null

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
        initRecycler()
        camUtil = CameraUtil(requireActivity())
        binding.cardApuntes.setOnClickListener { navController?.navigate(R.id.action_mainFragment_to_notesFragment) }
        binding.cardGrupos.setOnClickListener { Toast.makeText(context,"Grupos",Toast.LENGTH_LONG).show() }
        binding.addNote.setOnClickListener{
            camUtil!!.captureImage()
            imagenes = CommonUtils.listarImagenes()
        }
    }

    override fun onResume() {
        super.onResume()
        imagenes = CommonUtils.listarImagenes()
        camUtil?.resultNote()
        initRecycler()
//        imagenes?.forEach { println("IMAGENES -> $it") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initRecycler(){
        if(!imagenes.isNullOrEmpty()){
            val adapter = ImageAdapter(imagenes, context)
            binding.rvImages.layoutManager = LinearLayoutManager(context)
            binding.rvImages.adapter = adapter
        }
    }

}