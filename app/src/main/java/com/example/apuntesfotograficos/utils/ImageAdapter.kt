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
import com.squareup.picasso.Picasso

class ImageAdapter(titles: MutableList<String>?, images:MutableList<String>?, context: Context?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    lateinit var mListener: onItemClickListener

    var title: MutableList<String>? = titles
    val image: MutableList<String>? = images
    val context = context

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imgView: ImageView

        init {
            textView = view.findViewById(R.id.tv_name_card)
            imgView = view.findViewById(R.id.img_card)
            imgView.setOnClickListener { listener.onItemClick(adapterPosition) }
            imgView.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition)
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
        if(image?.get(position) == null || image?.get(position).isEmpty()){
            viewHolder.imgView.setImageResource(R.drawable.note_view);
        }else{
//            println("RECYCLER -> ${Uri.parse(image?.get(position))}")
           Picasso.get()
                .load("file://${image?.get(position)}")
                .error(R.drawable.share)
                .into(viewHolder.imgView);
        }
        viewHolder.textView.text = title?.get(position)
    }

    override fun getItemCount(): Int {
        return image!!.size
    }

    fun setOnItemListener(listener: onItemClickListener){
        mListener = listener
    }

}