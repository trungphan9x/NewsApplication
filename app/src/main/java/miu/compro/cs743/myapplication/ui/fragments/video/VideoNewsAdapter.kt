package miu.compro.cs743.myapplication.ui.fragments.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.databinding.ItemNewsVideoBinding
import miu.compro.cs743.myapplication.injection.GlideApp
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED

//class VideoNewsAdapter(private val articles: List<Article>) : RecyclerView.Adapter<VideoNewsAdapter.ViewHolder>() {
class VideoNewsAdapter() : ListAdapter<Article, VideoNewsAdapter.ViewHolder>(DiffCallback) {

    private val articles: ArrayList<Article> = arrayListOf()

    private var onItemClickListener: ((Int, Int, Article, View) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((Int, Int, Article, View) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    fun setItems(items: List<Article>) {
        this.articles.clear()
        this.articles.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoNewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoNewsAdapter.ViewHolder, position: Int) {
        holder.binding.tvTitle.text = articles[position].title
        holder.binding.tvSource.text = articles[position].source?.name
        holder.binding.tvPublishedDate.text = articles[position].publishedAtModified

        GlideApp.with(holder.binding.root.context)
            .load(articles[position].urlToImage)
            .apply(
                RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .placeholder(R.drawable.default_image)
            .into(holder.binding.imageView)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(ITEM_CLICKED, position, articles[position], holder.binding.root)
        }

        holder.binding.btnSetting.setOnClickListener {
            val popup = PopupMenu(it.context, it).apply {
                inflate(R.menu.three_dot_menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_bookmark ->    {
                            onItemClickListener?.invoke(BOOKMARK_CLICKED, position, articles[position], holder.binding.root)
                            true
                        }
                        else -> false
                    }
                }
            }
            popup.show()
        }

        holder.binding.btnShare.setOnClickListener {
            onItemClickListener?.invoke(SHARE_CLICKED, position, articles[position], holder.binding.root)
        }
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(val binding: ItemNewsVideoBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ChannelList]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        //Use Kotlin's referential equality operator (===), which returns true if the object references for oldItem and newItem are the same.
        //override fun areItemsTheSame(oldItem: PhotoDetailEntity, newItem: PhotoDetailEntity) = oldItem === newItem
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}