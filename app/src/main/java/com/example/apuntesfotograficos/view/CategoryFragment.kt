package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.apuntesfotograficos.utils.ImageAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoryFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    var noteDao = MainActivity.dbRoom.noteDao()
    lateinit var nameCategory:String

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
        binding.tvMainTitle.text = nameCategory
        initRecycler()
        binding.tvMainTitle.text = nameCategory
        binding.btnBackCategory.setOnClickListener {
            navController?.navigate(R.id.action_categoryFragment_to_notesFragment)
        }
    }

    fun initRecycler(){
        Handler().postDelayed(Runnable {
            try {
                lifecycleScope.launch {
                    var notes = noteDao.getNoteByCategory(nameCategory)
                    if(notes.size > 0){
                        val adapter = ImageAdapter(notes, context)
                        binding.rvImages.layoutManager = LinearLayoutManager(context)
                        binding.rvImages.adapter = adapter
                        adapter.setOnItemListener(object : onItemClickListener {
                            override fun onItemClick(position: Int) {
//                                Toast.makeText(context,"$position", Toast.LENGTH_LONG).show()
                                val bundle = bundleOf("src_image" to "${notes[position].note_src}")
                                navController?.navigate(R.id.action_categoryFragment_to_imageFragment, bundle)
                            }

                            override fun onItemLongClick(position: Int) {
                                MainActivity.uiUtils.createDialog()
                            }
                        })
                    }
                }
            }catch (e: Exception){e.printStackTrace()}
        },100)

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