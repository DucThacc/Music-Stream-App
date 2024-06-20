package com.example.musicstream

import android.content.Intent
import android.icu.text.Transliterator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstream.adapter.SectionSongListAdapter
import com.example.musicstream.adapter.SongsListAdapter
import com.example.musicstream.databinding.ActivityFindMusicBinding
import com.example.musicstream.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.musicstream.databinding.SongListItemRecyclerRowBinding
import com.example.musicstream.models.CategoryModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
import java.nio.file.WatchEvent

class FindMusicActivity : AppCompatActivity() {

    lateinit var binding : ActivityFindMusicBinding
    lateinit var songListAdapter : SongsListAdapter

    lateinit var songList: ArrayList<SongModel>


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAllSongs()

        binding.buttonFindMusic.setOnClickListener {
            val keyword = convertToUnsigned(binding.textFindMus.text.toString())
//            val keyword = binding.textFindMus.text.toString()
            if (keyword.isNotEmpty()) {
                searchNhac(keyword)
            } else {
                // Hiển thị thông báo rằng từ khóa tìm kiếm trống
                Toast.makeText(this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun showAllSongs() {
//        FirebaseFirestore.getInstance().collection("sections")
//            .document("all_song")
//            .get().addOnSuccessListener {
//                FirebaseFirestore.getInstance().collection("songs")
////                    .whereGreaterThanOrEqualTo("title", "168 giờ").whereLessThanOrEqualTo("title", "168 giờ\uf8ff")
////                    .whereEqualTo("title", "Anh")
////                    .whereArrayContains("title", "Anh")
//                    .whereGreaterThanOrEqualTo("title","168 giờ")
//                    .orderBy("count", Query.Direction.DESCENDING)
//                    .get()
//                    .addOnSuccessListener {songListSnapshot->
//                        val songsModelList= songListSnapshot.toObjects<SongModel>()
//                        val songsIdList = songsModelList.map { it.id }.toList()
//                        val section = it.toObject(CategoryModel::class.java)
//                        section?.apply {
//                            section.songs = songsIdList
//                            binding.songsListFindRecyclerView.layoutManager = LinearLayoutManager(this@FindMusicActivity,LinearLayoutManager.HORIZONTAL,false)
//                            binding.songsListFindRecyclerView.adapter = SectionSongListAdapter(songs)
//                            binding.MainlayoutFind.setOnClickListener {
//                                SongsListActivity.category = section
//                                startActivity(Intent(this@FindMusicActivity,SongsListActivity::class.java))
//                            }
//                        }
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.e("TAG", "Error getting documents: ", exception)
//                    }
//            }


        FirebaseFirestore.getInstance().collection("sections")
            .document("all_song")
            .get().addOnSuccessListener { sectionSnapshot ->
                val section = sectionSnapshot.toObject(CategoryModel::class.java)
                section?.apply {
                    val songsIdList = songs ?: emptyList()
                    binding.songsListFindRecyclerView.layoutManager = LinearLayoutManager(this@FindMusicActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.songsListFindRecyclerView.adapter = SectionSongListAdapter(songsIdList)
                    binding.MainlayoutFind.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@FindMusicActivity, SongsListActivity::class.java))
                    }
                }
            }


    }

    private fun searchNhac(keyword: String) {
        FirebaseFirestore.getInstance().collection("songs")
//            .whereGreaterThanOrEqualTo("title", keyword + "\uf8ff")
            .orderBy("title")
            .startAt(keyword)
            .endAt(keyword + "\uf8ff")
            .get()
            .addOnSuccessListener { songListSnapshot ->
                val songsModelList = songListSnapshot.toObjects<SongModel>()
                val songsIdList = songsModelList.map { it.id }.toList()
                val section = CategoryModel() // Tạo mới một đối tượng CategoryModel
                section?.apply {
                    section.songs = songsIdList
                    binding.songsListFindRecyclerView.layoutManager = LinearLayoutManager(this@FindMusicActivity,LinearLayoutManager.HORIZONTAL,false)
                    binding.songsListFindRecyclerView.adapter = SectionSongListAdapter(songs)
                    binding.MainlayoutFind.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@FindMusicActivity,SongsListActivity::class.java))
                    }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun convertToUnsigned(text: String): String {
        val transliterator = Transliterator.getInstance("Latin-ASCII")
        return transliterator.transliterate(text)
    }



}