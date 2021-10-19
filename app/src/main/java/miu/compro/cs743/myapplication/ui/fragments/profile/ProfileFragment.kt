package miu.compro.cs743.myapplication.ui.fragments.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        setObserver()
    }

    private fun setObserver() {
        viewModel.users.observe(viewLifecycleOwner, {
            viewModel.user = it.firstOrNull()
            setView()
        })
    }

    private fun setView() {
        when (sharedPreference.getIsLogIn()) {
            false -> {
                getNav(binding.root).navigate(R.id.action_navigation_profile_to_loginFragment)
            }
            true -> {
                when (viewModel.user) {
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
                        val user = viewModel.user
                        viewModel.user?.let {
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