package com.uwdp.whatyouwant.Search

import android.content.Context
import android.inputmethodservice.Keyboard
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uwdp.whatyouwant.databinding.ActivitySearchBinding
import com.uwdp.whatyouwant.databinding.ItemSearchBinding

class SearchAdapter(val context: Context, val items: MutableList<Items>)
    : RecyclerView.Adapter<SearchAdapter.Viewholder>() {
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
            val title = items[position].title
            val price = items[position].lprice

            binding.txtSearchtitle.text = Html.fromHtml(title).toString()
            binding.txtSearchprice.text = "최저가 " + price + "원"

            Glide.with(context)
                .load(item.image)
                .into(binding.imgSearchresult)

        }

    }


}