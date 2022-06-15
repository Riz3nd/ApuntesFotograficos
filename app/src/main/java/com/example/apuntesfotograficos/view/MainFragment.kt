package com.example.apuntesfotograficos.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.example.apuntesfotograficos.utils.Constans.Companion.URL_IMAGES
import com.example.apuntesfotograficos.utils.ImageAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), ICamera.View, View.OnClickListener {
    var navController: NavController? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var cameraPresenter:CameraPesenter
    var name:String = ""
    var timeStamp:String = ""
    var cate:String = ""
    var noteDao = MainActivity.dbRoom?.noteDao()
    var categoryDao = MainActivity.dbRoom?.categoryDao()
    var adapter:ImageAdapter? = null
    lateinit var rv_recent_notes:RecyclerView

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
        id_user = preferencias.getInt("id", 0)
        user_email = preferencias.getString("email", "").toString()
        binding.tvMainTitle.text = "${getString(R.string.main_title)} ${preferencias.getString("name", "")}"
        rv_recent_notes = binding.rvImages
        rv_recent_notes.layoutManager = LinearLayoutManager(context)
        initRecycler()
        binding.cardApuntes.setOnClickListener(this)
        binding.cardShareNotes.setOnClickListener(this)
        binding.addNote.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
//        initRecycler()
        isSaveNote()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initRecycler(){
        lifecycleScope.launch {
            var notes = noteDao?.getAllNotes(id_user)
            if(notes != null && notes.size > 0){
                notes = notes.reversed()
                showRecyler(notes)
            } else
                initRecycler()
        }
    }

    fun showRecyler(notes: List<Note>){
        adapter = ImageAdapter(notes, context)
        if (adapter != null) {
            rv_recent_notes.adapter = adapter
            adapter!!.setOnItemListener(object : onItemClickListener {
                override fun onItemClick(position: Int, id: Int) {
                    when (id) {
                        R.id.img_card -> {
                            val bundle = bundleOf("src_image" to "${notes[position].note_src}")
                            navController?.navigate(
                                R.id.action_mainFragment_to_recentImageFragment,
                                bundle
                            )
                        }
                        R.id.icon_like -> {
                            lifecycleScope.launch {
                                noteDao?.updateNoteLike(
                                    true,
                                    notes[position].note_name
                                )
                                initRecycler()
                            }
                        }
                    }
                }

                override fun onItemLongClick(position: Int, id: Int) {
                    when (id) {
                        R.id.img_card -> {
//                            uiUtils.createDialog()
                        }
                        R.id.icon_like -> {
                            lifecycleScope.launch {
                                noteDao?.updateNoteLike(false, notes[position].note_name)
                                initRecycler()
                            }
                        }
                    }
                }
            })
        }
    }

    fun isSaveNote(){
        var titles = CommonUtils.getListTitles()
        Handler(Looper.getMainLooper()).postDelayed({
            titles?.forEach {
                if(it.contains("$name")){
                    if(!name.isNullOrBlank()){
                        lifecycleScope.launch {
                            noteDao?.insertNote(Note(0, id_user, name,
                                cate,"${timeStamp}", "n/a",
                                false, false,"${URL_IMAGES}$it",0))
                            initRecycler()
                        }
                        name = ""
                    }
                }
            }
        },500)
    }

    override fun getCamera():Activity {
        return requireActivity()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_note -> {
                lifecycleScope.launch {
                    var listCategory = categoryDao?.getAllCategory(id_user)
                    var dialog = uiUtils.createDialogNote(listCategory)
                    val btnDialogOK = dialog.findViewById<Button>(R.id.btn_dialog_ok)
                    val etNameNote = dialog.findViewById<EditText>(R.id.et_name_note)
                    val spinner_category = dialog.findViewById<Spinner>(R.id.spinner_category)
                    btnDialogOK.setOnClickListener {
                        if(!etNameNote.text.isNullOrBlank()){
                            timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                            name = "${etNameNote.text}"
                            cate = "${spinner_category.selectedItem}"
                            cameraPresenter.getImage("${name}_${cate}", timeStamp)
                            dialog.dismiss()
                        }else{
                            Toast.makeText(context,"Debe ingresar un nombre", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            R.id.card_apuntes -> navController?.navigate(R.id.action_mainFragment_to_notesFragment)
            R.id.card_share_notes -> navController?.navigate(R.id.action_mainFragment_to_shareFragment)
            R.id.btn_profile -> {
                var dialog = uiUtils.profileDialog(object : onItemClickListener.onClickDialog{
                override fun onClickDialog() { navController?.navigate(R.id.action_mainFragment_to_loginFragment) } }
                )
                var numApunte = dialog.findViewById<TextView>(R.id.tv_num_apunte)
                var numLikes = dialog.findViewById<TextView>(R.id.tv_likes)
                var numCate = dialog.findViewById<TextView>(R.id.tv_num_cate)
                lifecycleScope.launch {
                    numApunte.text = "${noteDao?.getAllNotes(id_user)?.size}"
                    numLikes.text = "${noteDao?.getAllNotesLike(true, id_user)?.size}"
                    numCate.text = "${categoryDao?.getAllCategory(id_user)?.size}"
                }
            }
        }
    }

    companion object{
        var id_user = 0
        var user_email = ""
    }

}