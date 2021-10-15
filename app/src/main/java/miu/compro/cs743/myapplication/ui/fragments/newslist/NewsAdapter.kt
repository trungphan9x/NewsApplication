package miu.compro.cs743.myapplication.ui.fragments.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.databinding.ItemNewsPhotoBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED

class NewsAdapter(private val articles: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int, Int, Article, View) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((Int, Int, Article, View) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsPhotoBinding.inflate(
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

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(ITEM_CLICKED, position, articles[position], holder.binding.root)
        }

        holder.binding.btnBookmark.setOnClickListener {
            onItemClickListener?.invoke(BOOKMARK_CLICKED, position, articles[position], holder.binding.root)
        }

        holder.binding.btnShare.setOnClickListener {
            onItemClickListener?.invoke(SHARE_CLICKED, position, articles[position], holder.binding.root)
        }
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(val binding: ItemNewsPhotoBinding) : RecyclerView.ViewHolder(binding.root)
}