package miu.compro.cs743.myapplication.ui.fragments.video

import android.os.Bundle
import android.view.View
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentVideoBinding
import org.koin.android.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {
    private val videoViewModel: VideoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}