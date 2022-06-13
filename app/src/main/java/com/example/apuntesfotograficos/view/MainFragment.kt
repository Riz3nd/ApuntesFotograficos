package com.example.apuntesfotograficos.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.MainActivity.Companion.uiUtils
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentMainBinding
import com.example.apuntesfotograficos.interactor.Camera
import com.example.apuntesfotograficos.interfaces.ICamera
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.presenter.CameraPesenter
import com.example.apuntesfotograficos.utils.CommonUtils
import com.example.apuntesfotograficos.utils.ImageAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), ICamera.View, View.OnClickListener {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
//    var imagenes:MutableList<String>? = null
    lateinit var cameraPresenter:CameraPesenter
    var noteDao = MainActivity.dbRoom.noteDao()
    var categoryDao = MainActivity.dbRoom.categoryDao()

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
        val preferencias  = requireActivity().
        getSharedPreferences("userData", Context.MODE_PRIVATE)
        binding.tvMainTitle.text = "${getString(R.string.main_title)} ${preferencias.getString("name", "")}"
        initRecycler()
        binding.cardApuntes.setOnClickListener(this)
        binding.cardGrupos.setOnClickListener(this)
        binding.addNote.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initRecycler(){
        var imagenes = CommonUtils.getListImages()
        var titles = CommonUtils.getListTitles()
        if(!imagenes.isNullOrEmpty()){
            val adapter = ImageAdapter(titles, imagenes, context)
            binding.rvImages.layoutManager = LinearLayoutManager(context)
            binding.rvImages.adapter = adapter
            adapter.setOnItemListener(object : onItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(context,"$position",Toast.LENGTH_LONG).show()
                }

                override fun onItemLongClick(position: Int) {
                    uiUtils.createDialog()
                }

            })
        }
    }

    override fun getCamera():Activity {
        return requireActivity()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_note -> {
                lifecycleScope.launch {
//                    noteDao.insertNote(Note(0,"Nueva nota",1,"hoy",
//                        "saludos gente",false,"as123da",0))
                    var listCategory = categoryDao.getAllCategory()
                    var dialog = uiUtils.createDialogNote(/*cameraPresenter, */listCategory)
                    val btnDialogOK = dialog.findViewById<Button>(R.id.btn_dialog_ok)
                    val etNameNote = dialog.findViewById<EditText>(R.id.et_name_note)
                    val spinner_category = dialog.findViewById<Spinner>(R.id.spinner_category)
                    btnDialogOK.setOnClickListener {
                        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
/*                        lifecycleScope.launch {
                            noteDao.insertNote(Note(0,"${etNameNote.text}",
                                "${spinner_category.selectedItem}","${timeStamp}", "saludos gente",
                                false,"as123da",0))
                        }*/
                        var name = "${etNameNote.text}*${spinner_category.selectedItem}"
                        cameraPresenter.getImage(name, timeStamp)
                        println("NOTA ---------------> ${name}")
                        dialog.dismiss()
                    }

                }
            }
            R.id.card_apuntes -> navController?.navigate(R.id.action_mainFragment_to_notesFragment)
            R.id.card_grupos -> uiUtils.createDialog()
            R.id.btn_profile -> { uiUtils.profileDialog(object : onItemClickListener.onClickDialog{
                override fun onClickDialog() { navController?.navigate(R.id.action_mainFragment_to_loginFragment) } }) }
        }
    }

}