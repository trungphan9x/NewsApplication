package miu.compro.cs743.myapplication.ui.fragments.profile

import android.os.Bundle
import android.view.View
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentProfileBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}