package com.wingoku.kotlinnavigationcomponenttutorial.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wingoku.kotlinnavigationcomponenttutorial.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            val userName = tvUserName.text.toString()
            val password = tvPassword.text.toString()

            val action = LoginFragmentDirections.actionLoginFragmentToFinalFragment(userName, password)
            findNavController().navigate(action)
        }
    }
}