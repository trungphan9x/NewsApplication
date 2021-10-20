package miu.compro.cs743.myapplication.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentProfileBinding
import miu.compro.cs743.myapplication.model.data.Language
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_ARTICLE
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_IS_VIDEO
import miu.compro.cs743.myapplication.ui.activity.main.MainActivity
import miu.compro.cs743.myapplication.ui.activity.main.MainViewModel
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment
import miu.compro.cs743.myapplication.ui.fragments.newslist.PhotoNewsAdapter
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import miu.compro.cs743.myapplication.util.getCurrentUser
import miu.compro.cs743.myapplication.util.removeAllData
import miu.compro.cs743.myapplication.util.setCurrentUser
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate), AdapterView.OnItemSelectedListener, View.OnTouchListener  {
    private val viewModel: ProfileViewModel by viewModel()
    private val mainVM: MainViewModel by sharedViewModel()
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()
    private lateinit var photoAdapter: PhotoNewsAdapter
//    private lateinit var languageList: ArrayList<Language>
    var languages = hashMapOf<String,String>("English" to "en", "Vietnam" to "vn")
    private var isTouched: Boolean = false
    private var mainActivity: MainActivity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraDataFromLogin()
        setListener()
        setObserver()
        setRecyclerView()
        viewModel.getAllBookmark()
        setLanguageZone()
    }

    private fun setLanguageZone() {
        if (activity is MainActivity) {
            mainActivity = (activity as? MainActivity)
        }

        var aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages.keys.toTypedArray())
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.spinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@ProfileFragment
            setOnTouchListener(this@ProfileFragment)
            prompt = "Select your favourite language"
            gravity = Gravity.CENTER

        }
    }



    private fun getExtraDataFromLogin() {
        if(applicationContext().getCurrentUser() == null) {
            args.user?.let {
                applicationContext().setCurrentUser(it)
            }
        }
        setView()
    }

    private fun setObserver() {
        viewModel.article.observe(viewLifecycleOwner, {
            it?.let {
                if(!it.isNullOrEmpty()) {
                    binding.llProfileHolder.visibility = GONE
                    binding.rvArticles.visibility = VISIBLE
                    photoAdapter.setItems(it)
                } else {
                    binding.llProfileHolder.visibility = VISIBLE
                    binding.rvArticles.visibility = GONE
                }
            }
        })
    }

    private fun setListener() {
        binding.ivSetting.setOnClickListener {
            PopupMenu(requireActivity(), it).apply {
                menuInflater.inflate(R.menu.setting_popup_menu, this.menu)
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.logout -> {
                            applicationContext().removeAllData()
                            getNav(binding.root).navigate(R.id.action_navigation_profile_self)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete_all_bookmark -> {
                            viewModel.deleteAllBookmark()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                show()
            }
        }
    }

    private fun setView() {
        when (applicationContext().getCurrentUser()) {
            null -> {
                if (getNav(binding.root).currentDestination?.id == R.id.navigation_profile) {
                    getNav(binding.root).navigate(R.id.action_navigation_profile_to_loginFragment)
                }
            }
            else -> {
                val user = applicationContext().getCurrentUser()
                user?.let {
                    binding.tvEmail.text = it.email
                    "${it.firstname} ${it.lastname}".also {
                        binding.tvFullName.text = it
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)

        photoAdapter = PhotoNewsAdapter().apply {
            setOnItemClickListener { which, _, article, _ ->
                when (which) {
                    NewsListFragment.ITEM_CLICKED -> {
                        val intent = Intent(
                            requireActivity(),
                            ArticleDetailActivity::class.java
                        ).apply {
                            putExtra(DETAIL_ARTICLE, article)
                            putExtra(DETAIL_IS_VIDEO, false)
                        }
                        startActivity(intent)

                    }
                    NewsListFragment.UNBOOKMARK_CLICKED -> {
                        viewModel.deleteBookmark(article)
                    }
                    NewsListFragment.SHARE_CLICKED -> {
                        article.url?.let {
                            share(article.url)
                        }
                    }
                }
            }
        }
        binding.rvArticles.adapter = photoAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!isTouched) return
        mainVM.setSelectedLanguage(languages.values.elementAt(position))
        isTouched = false
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        isTouched = true
        return false
    }

}