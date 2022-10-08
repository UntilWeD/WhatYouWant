package com.uwdp.whatyouwant.Search

import android.content.Context
import android.content.Intent
import android.inputmethodservice.Keyboard
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uwdp.whatyouwant.databinding.ActivitySearchBinding
import com.uwdp.whatyouwant.databinding.ItemSearchBinding

class SearchAdapter(val context: Context, val items: List<Items>)
    : RecyclerView.Adapter<SearchAdapter.Viewholder>() {

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

        }

    }




}