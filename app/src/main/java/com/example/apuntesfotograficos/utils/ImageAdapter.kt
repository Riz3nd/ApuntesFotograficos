package com.example.apuntesfotograficos.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apuntesfotograficos.R
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class ImageAdapter(/*titles: Array<String>,*/ images:MutableList<String>?, context: Context?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
//    var title: Array<String> = titles
    val image: MutableList<String>? = images
    val context = context
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
        viewHolder.imgView.setImageBitmap(getBitmap(image?.get(position)))
//        if (context != null) {
//            Picasso.Builder(context).build().load(Uri.parse(image?.get(position))).into(viewHolder.imgView)
//        }

        //Condiciono para ver si el resultado es nulo o vació
/*        if(image?.get(position) == null || image?.get(position).isEmpty()){
            viewHolder.imgView.setImageResource(R.drawable.note_view); //reemplaza la imagen
        }else{
            //Pero si me devuelve una url uso la librería picasso
            println("RECYCLER -> ${Uri.parse(image?.get(position))}")
           Picasso.get()
                .load("file://${image?.get(position)}")
                .error(R.drawable.share) //en caso que la url no sea válida muestro otra imagen
                .into(viewHolder.imgView);
        }*/


    }

    override fun getItemCount(): Int {
        return image!!.size
    }



//    fun getLoacalBitmap( pathName: String) : Bitmap? {
//        try {
//            var fis: FileInputStream  =   FileInputStream(pathName);
//            val bitmapfactory = BitmapFactory.decodeStream(fis)
//
//        } catch ( e:FileNotFoundException) {
//            e.printStackTrace();
//            return null;
        }

        fun getBitmap( pathName: String?) : Bitmap?{
            var file : File =  File(pathName)
            var bm : Bitmap? = null
            if(file.exists()){
                bm = BitmapFactory.decodeFile(pathName)
            }
            return bm




        }