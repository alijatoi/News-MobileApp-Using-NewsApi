package com.example.greatenewsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listener : NewsItemClicked) : RecyclerView.Adapter<NewsViewsHolder>() {
    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewsHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewsHolder(view)
        view.setOnClickListener {
            listener.onItemClickied(items[viewHolder.adapterPosition])
        }
        return viewHolder    }


    override fun getItemCount(): Int {

        return items.size
    }
    override fun onBindViewHolder(holder: NewsViewsHolder, position: Int) {
        val currentItem  = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageURl).into(holder.image)

    }
    fun updateNews(updatedItems : ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

class NewsViewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
val  titleView : TextView = itemView.findViewById(R.id.title)
    val image: ImageView   = itemView.findViewById(R.id.image)
    val author : TextView = itemView.findViewById(R.id.author)

}
interface NewsItemClicked {
    fun onItemClickied(item :News)
}