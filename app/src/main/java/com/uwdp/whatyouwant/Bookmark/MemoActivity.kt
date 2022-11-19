package com.uwdp.whatyouwant.Bookmark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.uwdp.whatyouwant.R
import com.uwdp.whatyouwant.databinding.ActivityBookmarkBinding
import com.uwdp.whatyouwant.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    val DB_NAME = "sqlite.sql"
    val DB_VERSION = 1

    private lateinit var binding : ActivityMemoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        val helper = SqliteHelper(this,DB_NAME, DB_VERSION)
        var listData = mutableListOf<Memo>()
        val memos = helper.selectMemo()
        listData.addAll(memos)




        var pos = intent.getIntExtra("pos", 0)
        val memo = listData[pos]
        binding.txtMemo.text = memo.content

        setContentView(binding.root)

        binding.btnUpdatememo.setOnClickListener {
            binding.btnSavememo.visibility = View.VISIBLE
            binding.etMemo.visibility = View.VISIBLE
            binding.etMemo.setText("${memo.content}")
        }

        binding.btnSavememo.setOnClickListener {
            binding.btnSavememo.visibility = View.INVISIBLE
            binding.etMemo.visibility = View.INVISIBLE
            val updatedcontent = binding.etMemo.text.toString()
            val updatedmemo = Memo(memo.no,updatedcontent,System.currentTimeMillis())
            helper.updateMemo(updatedmemo)
            binding.etMemo.setText("")
            binding.txtMemo.text = updatedmemo.content
        }






    }
}