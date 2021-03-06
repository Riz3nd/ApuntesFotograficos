package com.example.apuntesfotograficos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentShareImagesBinding
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Note
import com.example.apuntesfotograficos.utils.ImageAdapter
import com.example.apuntesfotograficos.view.ShareFragment.Companion.share_cate
import kotlinx.coroutines.launch

class ShareImagesFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentShareImagesBinding? = null
    private val binding get() = _binding!!
    var noteDao = MainActivity.dbRoom?.noteDao()
    lateinit var nameCategory:String
    var adapter:ImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentShareImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameCategory = share_cate
        initRecycler()
        binding.tvMainTitle.text = nameCategory
        binding.btnBackCategory.setOnClickListener {
            navController?.navigate(R.id.action_shareImagesFragment_to_shareFragment)
        }
    }

    fun initRecycler(){
        lifecycleScope.launch {
            var notes = noteDao?.getNoteByCategoryName(nameCategory)?.reversed()
            if(notes != null && notes !!.size > 0){
                showRecyler(notes)
            }
        }
    }

    fun showRecyler(notes: List<Note>){
        adapter = ImageAdapter(notes, context,1)
        binding.rvImages.layoutManager = LinearLayoutManager(context)
        binding.rvImages.adapter = adapter
        adapter!!.setOnItemListener(object : onItemClickListener{
            override fun onItemClick(position: Int, id: Int) {
                when(id){
                    R.id.img_card -> {
                        val bundle = bundleOf("src_image" to "${notes[position].note_src}")
                        navController?.navigate(R.id.action_shareImagesFragment_to_shareImageViewFragment, bundle)
                    }
                    R.id.icon_like -> {
                        lifecycleScope.launch { noteDao?.updateNoteLike(true, notes[position].note_name) }
                        initRecycler()
                    }
                }
            }
            override fun onItemLongClick(position: Int, id: Int) {
                when(id){
                    R.id.img_card -> {
//                        MainActivity.uiUtils.createDialog()
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