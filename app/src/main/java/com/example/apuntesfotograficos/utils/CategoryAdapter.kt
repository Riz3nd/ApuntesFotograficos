package com.example.apuntesfotograficos.utils

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Note
import com.squareup.picasso.Picasso

class CategoryAdapter(notes:List<Note>, context: Context?) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    lateinit var mListener: onItemClickListener
    var mNote: List<Note> = notes
//    var title: MutableList<String>? = titles
//    val image: MutableList<String>? = images
    val context = context

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imgView: ImageView

        init {
            textView = view.findViewById(R.id.tv_name_card)
            imgView = view.findViewById(R.id.img_card)
            imgView.setOnClickListener { listener.onItemClick(adapterPosition, imgView.id) }
            imgView.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition, imgView.id)
                true
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_list_view, viewGroup, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(mNote.get(position).note_src == null || mNote.get(position).note_src.isEmpty()){
            viewHolder.imgView.setImageResource(R.drawable.note_view);
        }else{
            if(!mNote.get(position).note_name.isNullOrBlank()){
                Picasso.get()
                    .load("file://${mNote.get(position).note_src}")
                    .error(R.drawable.share)
                    .into(viewHolder.imgView);
                viewHolder.textView.text = mNote.get(position).note_name
            }
        }
    }

    override fun getItemCount(): Int {
        return mNote!!.size
    }

    fun setOnItemListener(listener: onItemClickListener){
        mListener = listener
    }

}