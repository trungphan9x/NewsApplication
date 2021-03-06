package miu.compro.cs743.myapplication.ui.fragments.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentRegisterBinding
import miu.compro.cs743.myapplication.model.roomdb.entity.User
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerVM: RegisterViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.buttonCreateAccount.setOnClickListener {
            val firstname = binding.edtFirstname.text.toString().trim()
            val lastname = binding.edtLastname.text.toString().trim()
            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (!firstname.isNullOrEmpty() && !lastname.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                val user = User(firstname, lastname, email, password)
                registerVM.insertUserToRoom(user)
            } else {
                Toast.makeText(requireContext(), getString(R.string.register_must_fill_in_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver() {
        registerVM.resultInsertUser.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), getString(R.string.register_sign_up_success), Toast.LENGTH_SHORT).show()
                getNav(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })
    }

}