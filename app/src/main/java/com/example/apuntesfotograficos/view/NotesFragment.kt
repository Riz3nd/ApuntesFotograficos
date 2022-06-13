package com.example.apuntesfotograficos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    var categoryDao = MainActivity.dbRoom.categoryDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackNotes.setOnClickListener { navController?.navigate(R.id.action_notesFragment_to_mainFragment) }
        binding.btnAddCategory.setOnClickListener {
            var dialog = uiUtils.createDialogCategory()
            val btnDialogCancel = dialog.findViewById<Button>(R.id.btn_dialog_ok)
            val etNameCat = dialog.findViewById<EditText>(R.id.et_name_cat)
            btnDialogCancel.setOnClickListener {
                var nameCat = etNameCat.text.toString()
                if(!nameCat.isNullOrBlank()){
                    lifecycleScope.launch {
                    categoryDao.insertCategory(Category(0, nameCat))
                    dialog.dismiss()
                    }
                }
                else
                    Toast.makeText(context, "Debe completar el campo!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}