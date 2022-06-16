package com.example.apuntesfotograficos.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.MainActivity.Companion.uiUtils
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentNotesBinding
import com.example.apuntesfotograficos.model.Category
import com.example.apuntesfotograficos.model.Share
import com.example.apuntesfotograficos.view.MainFragment.Companion.id_user
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    var categoryDao = MainActivity.dbRoom?.categoryDao()
    var shareDao = MainActivity.dbRoom?.shareDao()
    var noteDao = MainActivity.dbRoom?.noteDao()

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
                    categoryDao?.insertCategory(Category(0, id_user, nameCat))
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
                var arrayCat = categoryDao?.getAllCategoryName(id_user)
                if (arrayCat != null && arrayCat.size > 0) {
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
                    binding.lvCategory.setOnItemLongClickListener { adapterView, view, i, l ->
                        var mainDialog = uiUtils.createDialog("${arrayCat[i]}")
                        val btnDialogCancel = mainDialog.findViewById<Button>(R.id.btn_dialog_cancel)
                        val btnDialogShare = mainDialog.findViewById<LinearLayout>(R.id.btn_share)
                        val btnDialogDelete = mainDialog.findViewById<LinearLayout>(R.id.btn_delete)
                        val btnDialogEdit = mainDialog.findViewById<LinearLayout>(R.id.btn_edit)
                        btnDialogCancel.setOnClickListener { mainDialog.dismiss() }
                        btnDialogShare.setOnClickListener {
                            mainDialog.dismiss()
                            lifecycleScope.launch {
                                var dialog = uiUtils.createDialogCateShare()
                                val btnDialogOk = dialog.findViewById<Button>(R.id.btn_dialog_ok)
                                val et_user = dialog.findViewById<EditText>(R.id.et_share)
                                btnDialogOk.setOnClickListener {
                                    lifecycleScope.launch {
                                        if(!et_user.text.isNullOrBlank()){
                                            shareDao?.insertShare(Share(0,"${et_user.text}","${arrayCat[i]}"))
                                            dialog.dismiss()
                                        } else
                                            Toast.makeText(context,"Debe ingresar el correo", Toast.LENGTH_LONG).show()
                                    }

                                }
                            }
                        }
                        btnDialogDelete.setOnClickListener {
                            lifecycleScope.launch {
                                categoryDao?.deleteCategory("${arrayCat[i]}")
                                noteDao?.deleteNotes("${arrayCat[i]}")
                            }
                            loadCategory()
                            mainDialog.dismiss()
                        }
                        btnDialogEdit.setOnClickListener {
                            mainDialog.dismiss()
                            lifecycleScope.launch {
                                var dialog = uiUtils.createDialogEditCate()
                                val btnDialogOk = dialog.findViewById<Button>(R.id.btn_dialog_ok)
                                val et_user = dialog.findViewById<EditText>(R.id.et_share)
                                val tv_title = dialog.findViewById<TextView>(R.id.et_title)
                                tv_title.text = "Modificar"
                                et_user.hint = "Nuevo nombre"
                                btnDialogOk.setOnClickListener {
                                    lifecycleScope.launch {
                                        categoryDao?.updateCategory("${arrayCat[i]}", "${et_user.text}")
                                        noteDao?.updateNoteCategory("${et_user.text}", "${arrayCat[i]}")
                                    }
                                    dialog.dismiss()
                                    loadCategory()
                                }
                            }
                            loadCategory()
                        }
                        true
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