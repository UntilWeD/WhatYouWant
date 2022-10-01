package com.uwdp.whatyouwant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.uwdp.whatyouwant.databinding.ActivityMainBinding
import com.uwdp.whatyouwant.util.MyApplication


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)
        binding.addFab.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        makeRecyclerView()
    }

    private fun makeRecyclerView(){
        //컬렉션 모두 가져오기
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId=document.id //식별자값
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager=LinearLayoutManager(this)
                binding.mainRecyclerView.adapter= MyAdapter(this,itemList)
            }
            .addOnFailureListener{exception ->
                Log.d("WYW","Error getting documents: ", exception)
                Toast.makeText(this,"서버로부터 데이터 획득에 실패했습니다.",
                    Toast.LENGTH_SHORT).show()
            }


    }
}