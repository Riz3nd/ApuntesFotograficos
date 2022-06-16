package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentCategoryBinding
import com.example.apuntesfotograficos.databinding.FragmentLoginBinding
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.utils.ImageAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoryFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    var noteDao = MainActivity.dbRoom?.noteDao()
    lateinit var nameCategory:String
    var adapter:ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = findNavController()
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferencias  = requireActivity().
        getSharedPreferences("categoryNamePref", Context.MODE_PRIVATE)
        nameCategory = preferencias.getString("cateName", "") ?: "No disponible"
        initRecycler()
        binding.tvMainTitle.text = nameCategory
        binding.btnBackCategory.setOnClickListener {
            navController?.navigate(R.id.action_categoryFragment_to_notesFragment)
        }
    }

    fun initRecycler(){
        lifecycleScope.launch {
            var notes = noteDao?.getNoteByCategory(nameCategory, MainFragment.id_user)?.reversed()
            if(notes != null && notes !!.size > 0){
                showRecyler(notes)
            }
        }
    }

    fun showRecyler(notes: List<Note>){
        adapter = ImageAdapter(notes, context,0)
        binding.rvImages.layoutManager = LinearLayoutManager(context)
        binding.rvImages.adapter = adapter
        adapter!!.setOnItemListener(object : onItemClickListener{
            override fun onItemClick(position: Int, id: Int) {
                when(id){
                    R.id.img_card -> {
                        val bundle = bundleOf("src_image" to "${notes[position].note_src}")
                        navController?.navigate(R.id.action_categoryFragment_to_imageFragment, bundle)
                    }
                    R.id.icon_like -> {
                        lifecycleScope.launch { noteDao?.updateNoteLike(true, notes[position].note_name) }
                        initRecycler()
                    }
                }
            }
            override fun onItemLongClick(position: Int, id: Int) {
                when(id){
                    R.id.main_container_card -> {
                        var mainDialog = MainActivity.uiUtils.createDialog("${notes.get(position).note_name}")
                        val btnDialogCancel = mainDialog.findViewById<Button>(R.id.btn_dialog_cancel)
                        val btnDialogShare = mainDialog.findViewById<LinearLayout>(R.id.btn_share)
                        btnDialogShare.visibility = View.GONE
                        val btnDialogDelete = mainDialog.findViewById<LinearLayout>(R.id.btn_delete)
                        val btnDialogEdit = mainDialog.findViewById<LinearLayout>(R.id.btn_edit)
                        btnDialogCancel.setOnClickListener {
                            mainDialog.dismiss()
                        }
                        btnDialogDelete.setOnClickListener {
                            lifecycleScope.launch {
                                noteDao?.deleteNote("${notes.get(position).note_name}")
                            }
                            initRecycler()
                            mainDialog.dismiss()
                        }
                        btnDialogEdit.setOnClickListener {
                            mainDialog.dismiss()
                            lifecycleScope.launch {
                                var dialog = MainActivity.uiUtils.createDialogEditCate()
                                val btnDialogOk = dialog.findViewById<Button>(R.id.btn_dialog_ok)
                                val et_user = dialog.findViewById<EditText>(R.id.et_share)
                                val tv_title = dialog.findViewById<TextView>(R.id.et_title)
                                tv_title.text = "Modificar"
                                et_user.hint = "Nuevo nombre"
                                btnDialogOk.setOnClickListener {
                                    lifecycleScope.launch {
                                        noteDao?.updateNote("${et_user.text}", "${notes.get(position).note_name}")
                                    }
                                    dialog.dismiss()
                                    initRecycler()
                                }
                            }
                        }
                    }
                    R.id.icon_like -> {
                        lifecycleScope.launch {
                            noteDao?.updateNoteLike(false, notes[position].note_name) }
                        initRecycler()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        initRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}