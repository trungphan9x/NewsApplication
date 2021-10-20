package miu.compro.cs743.myapplication.ui.fragments.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.databinding.ItemNewsPhotoBinding
import miu.compro.cs743.myapplication.injection.GlideApp
import miu.compro.cs743.myapplication.model.enum.CurrentTab
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.UNBOOKMARK_CLICKED
import miu.compro.cs743.myapplication.util.getCurrentTab

//class PhotoNewsAdapter(private val articles: List<Article>) : RecyclerView.Adapter<PhotoNewsAdapter.ViewHolder>() {
class PhotoNewsAdapter() : ListAdapter<Article, PhotoNewsAdapter.ViewHolder>(DiffCallback) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoNewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoNewsAdapter.ViewHolder, position: Int) {
        holder.binding.tvTitle.text = articles[position].title
        holder.binding.tvSource.text = articles[position].source?.name
        holder.binding.tvPublishedDate.text = articles[position].publishedAtModified
        holder.binding.btnSetting

        GlideApp.with(holder.binding.root.context)
            .load(articles[position].urlToImage)
            .placeholder(R.drawable.default_image)
            .into(holder.binding.ivImage)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(ITEM_CLICKED, position, articles[position], holder.binding.root)
        }

        holder.binding.btnSetting.setOnClickListener {
            val popup = PopupMenu(it.context, it).apply {
                inflate(R.menu.three_dot_menu)
                this.menu.findItem(R.id.menu_bookmark).apply {
                    when(applicationContext().getCurrentTab()) {
                        CurrentTab.PROFILE.name -> this.title = applicationContext().getString(R.string.profile_delete_bookmark)
                    }
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_bookmark ->    {
                            onItemClickListener?.invoke(if(applicationContext().getCurrentTab()==CurrentTab.PROFILE.name) UNBOOKMARK_CLICKED else BOOKMARK_CLICKED, position, articles[position], holder.binding.root)
                            true
                        }
                        else -> false
                    }
                }
            }
            popup.show()
            true
        }

        holder.binding.btnShare.setOnClickListener {
            onItemClickListener?.invoke(SHARE_CLICKED, position, articles[position], holder.binding.root)
        }
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(val binding: ItemNewsPhotoBinding) : RecyclerView.ViewHolder(binding.root)

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