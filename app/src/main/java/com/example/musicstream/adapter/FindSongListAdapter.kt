package com.example.musicstream.adapter

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Strings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstream.MyExoplayer
import com.example.musicstream.PlayerActivity
import com.example.musicstream.databinding.SongListItemRecyclerRowBinding
import com.example.musicstream.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore

class FindSongListAdapter(private val songFindList : List<SongModel>) :
    RecyclerView.Adapter<FindSongListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SongListItemRecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(song :SongModel){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SongListItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songFindList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listnhac = songFindList[position]



    }


}