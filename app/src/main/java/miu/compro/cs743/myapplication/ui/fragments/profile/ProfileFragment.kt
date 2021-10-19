package miu.compro.cs743.myapplication.ui.fragments.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentProfileBinding
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModel()
    private val sharedPreference: NewsAppSharedPreference by inject()
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraDataFromLogin()
        setListener()
    }

    private fun getExtraDataFromLogin() {
        if(sharedPreference.getCurrentUser() == null) {
            args.user?.let {
                sharedPreference.setCurrentUser(it)
            }
        }
        setView()
    }

    private fun setListener() {
        binding.ivSetting.setOnClickListener {
            PopupMenu(requireActivity(), it).apply {
                menuInflater.inflate(R.menu.setting_popup_menu, this.menu)
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.logout -> {
                            sharedPreference.removeAllData()
                            getNav(binding.root).navigate(R.id.action_navigation_profile_self)
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
        when (sharedPreference.getIsLogIn()) {
            false -> {
                getNav(binding.root).navigate(R.id.action_navigation_profile_to_loginFragment)
            }
            true -> {
                when (sharedPreference.getCurrentUser()) {
                    null -> {
                        Toast.makeText(
                            requireContext(),
                            "There is something wrong! Please try to log in again",
                            Toast.LENGTH_SHORT
                        ).show()
                        if (getNav(binding.root).currentDestination?.id == R.id.navigation_profile) {
                            getNav(binding.root).navigate(R.id.action_navigation_profile_to_loginFragment)
                        }
                    }
                    else -> {
                        val user = sharedPreference.getCurrentUser()
                        user?.let {
                            binding.tvEmail.text = it.email
                            "${it.firstname} ${it.lastname}".also {
                                binding.tvFullName.text = it
                            }
                        }
                    }
                }
            }
        }
    }

}