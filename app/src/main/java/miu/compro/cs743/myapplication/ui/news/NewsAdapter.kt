package miu.compro.cs743.myapplication.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.databinding.ItemNewsBinding
import miu.compro.cs743.myapplication.model.remote.response.Article

class NewsAdapter(private val articles: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.binding.tvTitle.text = articles[position].title
        holder.binding.tvSource.text = articles[position].source.name
        holder.binding.tvPublishedDate.text = articles[position].publishedAt

        Glide.with(holder.binding.root.context)
            .load(articles[position].urlToImage)
            .placeholder(R.drawable.default_image)
            .into(holder.binding.ivImage)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)
}