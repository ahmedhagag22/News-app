package com.example.new_app.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.new_app.R
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.databinding.NewsItemRecyclerBinding

class NewsAdapter(var item:List<News?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val viewBinding: NewsItemRecyclerBinding):RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var viewBinding=NewsItemRecyclerBinding.inflate(LayoutInflater.from(parent.context)
                ,parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.authorNews.text=item?.get(position)?.author
        holder.viewBinding.title.text=item?.get(position)?.title
        holder.viewBinding.timeNews.text=item?.get(position)?.publishedAt

        //load data by the url > gilde
        Glide.with(holder.itemView)
            .load(item?.get(position)?.urlToImage)
            .placeholder(R.drawable.ic_wifi)
            .into(holder.viewBinding.imageNews)
    }

    override fun getItemCount(): Int {
        return item?.size?:0
    }

    fun changeDate(articles: List<News?>?) {
        item=articles
        notifyDataSetChanged()

    }
}