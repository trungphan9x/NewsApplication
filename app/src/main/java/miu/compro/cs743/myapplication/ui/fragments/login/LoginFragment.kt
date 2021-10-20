package miu.compro.cs743.myapplication.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        setObserver()
        setListener()
    }

    private fun setObserver() {
        viewModel.users.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.userList = it
            }
        })
    }

    private fun setListener() {
        binding.btnCreateNewAccount.setOnClickListener {
            getNav(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.buttonSignIn.setOnClickListener {
            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !viewModel.userList.isNullOrEmpty() && viewModel.userList!!.any { it.email == email && it.password == password}) {
                val currentUser = viewModel.userList!!.find { it.email == email && it.password == password }
                getNav(binding.root).navigate(R.id.action_loginFragment_to_navigation_profile, bundleOf("user" to currentUser))
            } else {
                Toast.makeText(requireContext(), getString(R.string.login_username_pass_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }
}