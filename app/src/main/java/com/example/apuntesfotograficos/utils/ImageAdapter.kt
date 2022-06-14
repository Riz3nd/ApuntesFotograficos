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

class ImageAdapter(notes:List<Note>?, context: Context?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    lateinit var mListener: onItemClickListener
    var mNote: List<Note>? = notes
    val context = context

    class ViewHolder(view: View, listener: onItemClickListener,context: Context?) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imgView: ImageView
        val favImg: ImageView

        init {
            textView = view.findViewById(R.id.tv_name_card)
            imgView = view.findViewById(R.id.img_card)
            favImg = view.findViewById(R.id.icon_like)
            favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
            favImg.setOnClickListener {
                favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_heart_ok))
                listener.onItemClick(adapterPosition, favImg.id) }
            favImg.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition, favImg.id)
                favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                true
            }
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
        return ViewHolder(view, mListener, view.context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(mNote?.get(position)?.note_src == null || mNote?.get(position)!!.note_src.isEmpty()){
            viewHolder.imgView.setImageResource(R.drawable.note_view);
        }else{
            if(!mNote?.get(position)?.note_name.isNullOrBlank()){
//                var likeStatus = mNote?.get(position)?.note_like
                Picasso.get()
                    .load("file://${mNote?.get(position)?.note_src}")
                    .error(R.drawable.share)
                    .into(viewHolder.imgView);
                viewHolder.textView.text = mNote?.get(position)?.note_name
                if (mNote?.get(position)?.note_like!!)
                    viewHolder.favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_heart_ok))
            }
        }
    }

    override fun getItemCount(): Int {
        if (!mNote.isNullOrEmpty())
            return mNote!!.size
        else
            return 0
    }

    fun setOnItemListener(listener: onItemClickListener){
        mListener = listener
    }

}