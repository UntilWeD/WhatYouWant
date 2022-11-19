package com.uwdp.whatyouwant.Bookmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uwdp.whatyouwant.R
import com.uwdp.whatyouwant.Search.Items
import com.uwdp.whatyouwant.Search.ResultGetSearchItems
import com.uwdp.whatyouwant.databinding.ActivityBookmarkBinding
import com.uwdp.whatyouwant.databinding.ItemSearchBinding
import com.uwdp.whatyouwant.util.FBAuth
import com.uwdp.whatyouwant.util.FBRef
import kotlin.math.log

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBookmarkBinding
    lateinit var rvAdapter: BookmarkAdapter

    val DB_NAME = "sqlite.sql"
    val DB_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val helper = SqliteHelper(this,DB_NAME, DB_VERSION)

        val memos = helper.selectMemo()


        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookmarkitems = ArrayList<Items>()
        val bookmarkkeyList = ArrayList<String>()
        var myuid= FBAuth.getUid()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkkeyList.clear()
                bookmarkitems.clear()
                Log.d("Jhin",bookmarkitems.toString())
                for(Items in dataSnapshot.child("${myuid}").children){
                    bookmarkkeyList.add(Items.key.toString())
                    val bookmarks = Items.getValue(com.uwdp.whatyouwant.Search.Items::class.java)
                    bookmarkitems.add(bookmarks!!)
                }
                rvAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("SearchActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }



        FBRef.bookmarkRef.addValueEventListener(postListener)



        rvAdapter = BookmarkAdapter(this,bookmarkitems, bookmarkkeyList)
        rvAdapter.listData.addAll(memos)
        val rv:RecyclerView = binding.RecBookmark
        rv.adapter= rvAdapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)




    }

}
