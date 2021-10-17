package miu.compro.cs743.myapplication.ui.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentSearchBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsAdapter
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by viewModel()

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
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
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
                binding.rvArticles.adapter = NewsAdapter(articles).apply {
                    setOnItemClickListener { which, position, article, rootView ->
                        when (which) {
                            ITEM_CLICKED -> {
                                val intent = Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                                    putExtra("article", article)
                                }
                                startActivity(intent)

                            }
                            BOOKMARK_CLICKED -> {

                            }
                            SHARE_CLICKED -> {

                            }
                        }
                    }
                }
            }
        })
    }
}