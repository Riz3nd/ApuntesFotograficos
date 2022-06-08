package com.example.apuntesfotograficos.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentMainBinding
import com.example.apuntesfotograficos.interactor.Camera
import com.example.apuntesfotograficos.interfaces.ICamera
import com.example.apuntesfotograficos.presenter.CameraPesenter
import com.example.apuntesfotograficos.utils.CommonUtils
import com.example.apuntesfotograficos.utils.UIUtils
import com.example.apuntesfotograficos.utils.ImageAdapter

class MainFragment : Fragment(), ICamera.View, View.OnClickListener {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    var imagenes:MutableList<String>? = null
    lateinit var cameraPresenter:CameraPesenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        cameraPresenter = CameraPesenter(this, Camera())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        binding.cardApuntes.setOnClickListener(this)
        binding.cardGrupos.setOnClickListener(this)
        binding.addNote.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        imagenes = CommonUtils.getListImages()
        initRecycler()
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

    override fun getCamera():Activity {
        return requireActivity()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_note -> cameraPresenter?.getImage()
            R.id.card_apuntes -> navController?.navigate(R.id.action_mainFragment_to_notesFragment)
            R.id.card_grupos -> UIUtils.createDialog(requireActivity())
        }
    }


}