package com.example.apuntesfotograficos.utils

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apuntesfotograficos.R

class ImageAdapter(/*titles: Array<String>,*/ images:MutableList<String>?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
//    var title: Array<String> = titles
    val image: MutableList<String>? = images

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView
        val imgView: ImageView

        init {
//            textView = view.findViewById(R.id.img_card)
            imgView = view.findViewById(R.id.img_card)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_list_view, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.textView.text = title.get(position)
        viewHolder.imgView.setImageURI(Uri.parse(image?.get(position)))
    }

    override fun getItemCount(): Int {
        return image!!.size
    }

}