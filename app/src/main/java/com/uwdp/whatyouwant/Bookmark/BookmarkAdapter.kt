package com.uwdp.whatyouwant.Bookmark

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uwdp.whatyouwant.R
import com.uwdp.whatyouwant.Search.Items
import com.uwdp.whatyouwant.Search.SearchActivity
import com.uwdp.whatyouwant.Search.SearchAdapter
import com.uwdp.whatyouwant.Search.SearchAdapter.Viewholder
import com.uwdp.whatyouwant.databinding.ActivityBookmarkBinding
import com.uwdp.whatyouwant.databinding.ActivityMemoBinding
import com.uwdp.whatyouwant.databinding.ItemSearchBinding
import com.uwdp.whatyouwant.util.FBAuth
import com.uwdp.whatyouwant.util.FBRef

class BookmarkAdapter(val context: Context, val items: List<Items>, val keyList: ArrayList<String>)
    : RecyclerView.Adapter<BookmarkAdapter.Viewholder>() {

    val DB_NAME = "sqlite.sql"
    val DB_VERSION = 1
    val helper = SqliteHelper(context,DB_NAME, DB_VERSION)


    //메모아이템
    var listData = mutableListOf<Memo>()

    interface OnItemClickListener{
        fun onItemClick(v: View, item: Items, pos:Int)
    }
    private var listener : OnItemClickListener? = null

    fun SetOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Viewholder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.binditems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binditems(item : Items){

            binding.txtSearchtitle.text = Html.fromHtml(item.title.toString())
            binding.txtSearchprice.text = "최저가 " + item.lprice + "원"

            Glide.with(context)
                .load(item.image)
                .into(binding.imgSearchresult)

            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION){
                itemView.setOnClickListener{
                    listener?.onItemClick(itemView,item,pos)
                }

            }

            val btn_bookmark = itemView.findViewById<ImageView>(R.id.btn_bookmark)
            btn_bookmark.setOnClickListener{
                FBRef.bookmarkRef.child(FBAuth.getUid()).child(keyList[pos]).removeValue()
                notifyDataSetChanged()
                helper.deleteMemo(listData[pos])
            }

            val btn_memo = itemView.findViewById<Button>(R.id.btn_memo)

            btn_memo.setOnClickListener{
                Intent(context,MemoActivity::class.java).apply {
                    val memo = Memo(null,"메모작성",System.currentTimeMillis())
                    helper.insertMemo(memo)
                    putExtra("data",item)
                    putExtra("pos",pos)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }

//                if(binding.txtSearchprice.visibility == View.VISIBLE){
//                    binding.txtSearchprice.visibility = View.INVISIBLE
//                    binding.txtSearchtitle.visibility = View.INVISIBLE
//                    binding.txtMemo.visibility = View.VISIBLE
//                } else{
//                    binding.txtSearchprice.visibility = View.VISIBLE
//                    binding.txtSearchtitle.visibility = View.VISIBLE
//                    binding.txtMemo.visibility = View.INVISIBLE
//                }
            }



        }
    }
}