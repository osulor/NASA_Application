package com.example.nasatest.ui.adepter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasatest.data.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_nasa.view.*

class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(item: Item) {
        itemView.tvTitle.text = item.data[0].title
        itemView.tvAuthor.text = item.data[0].photographer
        itemView.tvDate.text = item.data[0].createdDate
        Picasso.get().load(item.links[0].image).into(itemView.ivNasaImage)
    }
}