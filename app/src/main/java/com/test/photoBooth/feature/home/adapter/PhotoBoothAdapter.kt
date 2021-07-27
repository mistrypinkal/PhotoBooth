package com.test.photoBooth.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.external.database.PhotoBooth
import com.test.photoBooth.R
import id.zelory.compressor.Compressor
import java.io.File

class PhotoBoothAdapter(var itemList: List<PhotoBooth>) :
    RecyclerView.Adapter<PhotoBoothAdapter.MyViewHolder>() {


    private var items: List<PhotoBooth>

    init {
        this.items = itemList
    }

    fun setData(items: List<PhotoBooth>) {
        this.items = items;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_photo, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Gets the number of Items in the list
    override fun getItemCount(): Int = items.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPhoto: ImageView
        private var context: Context
        fun bind(photoBooth: PhotoBooth?) {
            if (photoBooth != null) {
                val compressedFile: File? = try {
                    var file = File(photoBooth.photoLocalURL)
                    file = compressImage(Compressor(context), file);
                    file
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }

                Glide.with(context)
                    .load(compressedFile)
                    .into(ivPhoto)
            }
        }

        init {
            ivPhoto = view.findViewById(R.id.ivPhoto)
            context = itemView.context
        }
    }

    fun compressImage(compressor: Compressor, imageFile: File?): File {
        return compressor.compressToFile(imageFile)
    }

}