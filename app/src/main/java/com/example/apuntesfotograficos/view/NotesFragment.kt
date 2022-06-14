package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.MainActivity.Companion.uiUtils
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentNotesBinding
import com.example.apuntesfotograficos.model.Category
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    var categoryDao = MainActivity.dbRoom?.categoryDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackNotes.setOnClickListener { navController?.navigate(R.id.action_notesFragment_to_mainFragment) }
        binding.btnAddCategory.setOnClickListener {
            var dialog = uiUtils.createDialogCategory()
            val btnDialogOk = dialog.findViewById<Button>(R.id.btn_dialog_ok)
            val etNameCat = dialog.findViewById<EditText>(R.id.et_name_cat)
            btnDialogOk.setOnClickListener {
                var nameCat = etNameCat.text.toString()
                if(!nameCat.isNullOrBlank()){
                    lifecycleScope.launch {
                    categoryDao?.insertCategory(Category(0, nameCat))
                    dialog.dismiss()
                    loadCategory()
                    }
                }
                else
                    Toast.makeText(context, "Debe ingresar un nombre", Toast.LENGTH_LONG).show()
            }
        }
        loadCategory()
    }

    fun loadCategory(){
        Handler().postDelayed(Runnable {
            lifecycleScope.launch {
                var arrayCat = categoryDao?.getAllCategoryName()
                if (arrayCat!!.size > 0) {
                    var adapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        arrayCat
                    )
                    binding.lvCategory.adapter = adapter
                    binding.lvCategory.setOnItemClickListener { adapterView, view, i, l ->
//                        val bundle = bundleOf("category_name" to "${arrayCat[i]}")
                        val preferencias  = requireActivity().
                            getSharedPreferences("categoryNamePref", Context.MODE_PRIVATE)
                        val editor = preferencias.edit()
                        editor.putString("cateName", "${arrayCat[i]}")
                        editor.commit()
                        navController?.navigate(R.id.action_notesFragment_to_categoryFragment)
                    }
                }
            }
        },100)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}