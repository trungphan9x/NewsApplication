package miu.compro.cs743.myapplication.ui.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentSearchBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.PhotoNewsAdapter
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import org.koin.android.viewmodel.ext.android.viewModel
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_ARTICLE
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_IS_VIDEO
import miu.compro.cs743.myapplication.ui.activity.main.MainActivity
import miu.compro.cs743.myapplication.util.getCurrentUser


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var photoAdapter: PhotoNewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu_search, menu)
        initializeSearchView(menu)
    }


    private fun initializeSearchView(menu: Menu?) {
        val search = menu?.findItem(R.id.menu_search)
        //val searchManager = requireActivity().getSystemService(SEARCH_SERVICE) as SearchManager

        val searchView = search?.actionView as? SearchView
        searchView?.apply {
            //setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            isQueryRefinementEnabled = true;
            setIconifiedByDefault(true)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            setOnQueryTextListener(this@SearchFragment)
        }

    }

    override fun onQueryTextSubmit(keyword: String?): Boolean {
        searchViewModel.searchArticleByKeyword(keyword)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun observeData() {
        searchViewModel.article.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                binding.tvErrorNotification.visibility = GONE
                photoAdapter.setItems(it)
            }
        })

        searchViewModel.error.observe(viewLifecycleOwner, {
            binding.tvErrorNotification.visibility = VISIBLE
            binding.tvErrorNotification.text = it
        })
    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)

        photoAdapter = PhotoNewsAdapter().apply {
            setOnItemClickListener { which, position, article, rootView ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(
                            requireActivity(),
                            ArticleDetailActivity::class.java
                        ).apply {
                            putExtra(DETAIL_ARTICLE, article)
                            putExtra(DETAIL_IS_VIDEO, false)
                        }
                        startActivity(intent)

                    }
                    BOOKMARK_CLICKED -> {
                        if(applicationContext().getCurrentUser() != null) {
                            searchViewModel.insert(article)
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.news_list_noti_login_to_save_bookmark), Toast.LENGTH_SHORT).show()
                        }
                    }
                    SHARE_CLICKED -> {
                        article.url?.let {
                            share(article.url)
                        }
                    }
                }
            }
        }
        binding.rvArticles.adapter = photoAdapter
    }


    private fun initializeToolbar() {

        val toolbar = Toolbar(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 168
        )
        toolbar.layoutParams = layoutParams
//        toolbar.popupTheme = R.style.
//        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.title = null
        toolbar.visibility = View.VISIBLE
        toolbar.setContentInsetsAbsolute(0, 0)

        // Assuming in activity_main, you are using LinearLayout as root
        binding.llHolderSearchFragment.addView(toolbar, 0)

        //mainActivity?.setSupportActionBar(toolbar)
    }
}