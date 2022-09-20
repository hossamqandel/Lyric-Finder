package com.android.lyricfinder.feature_search.presentation

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.lyricfinder.R
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var data: List<SearchEntity>
    private lateinit var iCustomClick: ICustomClick

    fun setSearchData(data: List<SearchEntity>){
        this.data = data
        if (data.isNotEmpty()) notifyItemChanged(data.size - 1)
    }

    fun setOnItemClick(iCustomClick: ICustomClick){
        this.iCustomClick = iCustomClick
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder((view))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.songTitle.text = data[position].songTitle
        holder.artistName.text = data[position].artistName
        try {
            Glide.with(holder.songImg.context)
                .load(data[position].imageUrl)
                .into(holder.songImg)
        }catch (e: Exception){
            Log.e(TAG, "onBindViewHolder: ${e.cause}", )
        }
    }

    override fun getItemCount(): Int = data.size


   inner class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
       val songImg: ImageView = itemView.findViewById(R.id.iv_songImage)
       val songTitle: TextView = itemView.findViewById(R.id.tv_songTitle)
       val artistName: TextView = itemView.findViewById(R.id.tv_artistName)
       private val getLyrics: TextView = itemView.findViewById(R.id.btn_getLyrics)

       init {
           getLyrics.setOnClickListener {
               iCustomClick.onItemClick(data[layoutPosition].songId)
           }
       }
    }
}
