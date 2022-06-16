package com.example.apuntesfotograficos.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apuntesfotograficos.R
import com.example.apuntesfotograficos.interfaces.onItemClickListener
import com.example.apuntesfotograficos.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL


class ImageAdapter(notes:List<Note>?, context: Context?, type: Int) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    lateinit var mListener: onItemClickListener
    var mNote: List<Note>? = notes
    val context = context

    companion object{
        var typeV = 0
    }

    init{
        typeV = type
    }

    class ViewHolder(view: View, listener: onItemClickListener,context: Context?) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imgView: ImageView
        val mainContainer: ConstraintLayout
        val favImg: ImageView

        init {
            textView = view.findViewById(R.id.tv_name_card)
            imgView = view.findViewById(R.id.img_card)
            mainContainer = view.findViewById(R.id.main_container_card)
            favImg = view.findViewById(R.id.icon_like)
            if (typeV == 0)
                favImg.visibility = View.GONE
            else if (typeV == 1)
                favImg.visibility = View.VISIBLE
            favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_favorite))
            favImg.setOnClickListener {
                favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_favorite_ok))
                listener.onItemClick(adapterPosition, favImg.id) }
            favImg.setOnLongClickListener {
                favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_favorite))
                listener.onItemLongClick(adapterPosition, favImg.id)
                true
            }
            mainContainer.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition, mainContainer.id)
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
                Glide
                    .with(context!!)
                    .load("file://${mNote?.get(position)?.note_src}")
                    .centerCrop()
                    .into(viewHolder.imgView);
                viewHolder.textView.text = mNote?.get(position)?.note_name
                if (mNote?.get(position)?.note_like!!)
                    viewHolder.favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_favorite_ok))
                else
                    viewHolder.favImg.setImageDrawable(context?.getDrawable(R.drawable.ic_favorite))
            }
        }
    }

    private var image: Bitmap? = null
    private fun getBitmapFromURL(src: String?) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            try {
//                val url = URL(src)
                val bitmap = MediaStore.Images.Media.getBitmap(context?.getContentResolver(), Uri.parse(src))
                image = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
//                val bitMap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                // Log exception
            }
        }
    }

    override fun getItemCount(): Int {
        if (!mNote.isNullOrEmpty())
            return mNote!!.size
        else
            return 0
    }

    fun goneFavorite(){

    }

    fun setOnItemListener(listener: onItemClickListener){
        mListener = listener
    }

}