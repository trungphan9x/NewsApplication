package miu.compro.cs743.myapplication.ui.fragments.profile

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.View.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentProfileBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_ARTICLE
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_IS_VIDEO
import miu.compro.cs743.myapplication.ui.activity.main.MainActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment
import miu.compro.cs743.myapplication.ui.fragments.newslist.PhotoNewsAdapter
import miu.compro.cs743.myapplication.util.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

open class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate)  {
    private val viewModel: ProfileViewModel by viewModel()
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()
    private lateinit var photoAdapter: PhotoNewsAdapter
    private lateinit var currentPhotoPath: String
    private var capturedImageURI: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraDataFromLogin()
        setListener()
        viewModel.getAllBookmark()
        setObserver()
        setRecyclerView()
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
                            AppDialog.showDialog(requireContext(), getString(R.string.notification_delete_all_bookmarks)) {
                                viewModel.deleteAllBookmark()
                            }
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                show()
            }
        }

        binding.ivAvatar.setOnClickListener {
            capturedImageURI = pickFromCamera()
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
                requireActivity().getCurrentUriPicture()?.let {
                    val uri = Uri.parse(it)
                    binding.ivAvatar.setImageURI(uri)
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
                        AppDialog.showDialog(requireContext(), getString(R.string.notification_delete_one_bookmark) + article.title) {
                            viewModel.deleteBookmark(article)
                        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            capturedImageURI?.also {
                binding.ivAvatar.setImageURI(it)
                requireActivity().setCurrentUriPicture(it)
            }
        }
    }

    private fun pickFromCamera(): Uri? {
        //Create an Intent with action as ACTION_PICK
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val timeStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fname = "TmpImage$timeStamp.jpg"
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, fname)
            val capturedImageURI = requireActivity().contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageURI)
            values.clear()
            startActivityForResult(
                intent,
                REQUEST_IMAGE_CAPTURE
            )
            return capturedImageURI
        } catch (e: Exception) {
            return null
        }

    }


    companion object {
        const val REQUEST_IMAGE_CAPTURE = 101
    }
}