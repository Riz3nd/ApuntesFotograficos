package com.example.apuntesfotograficos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.apuntesfotograficos.MainActivity
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.databinding.FragmentShareBinding
import com.example.apuntesfotograficos.view.MainFragment.Companion.user_email
import kotlinx.coroutines.launch

class ShareFragment : Fragment() {
    var navController: NavController? = null
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!
    var shareDao = MainActivity.dbRoom?.shareDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategory()
        binding.btnBackNotes.setOnClickListener { navController?.navigate(R.id.action_shareFragment_to_mainFragment) }
    }

    fun loadCategory(){
        lifecycleScope.launch {
            var arrayCat = shareDao?.getShareAllCategory(user_email)
            if (arrayCat!!.size > 0) {
                var adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    arrayCat
                )
                binding.lvShare.adapter = adapter
                binding.lvShare.setOnItemClickListener { adapterView, view, i, l ->
                share_cate = "${arrayCat[i]}"
                navController?.navigate(R.id.action_shareFragment_to_shareImagesFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        var share_cate = ""
    }

}