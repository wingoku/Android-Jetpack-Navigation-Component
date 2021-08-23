package com.wingoku.kotlinnavigationcomponenttutorial.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wingoku.kotlinnavigationcomponenttutorial.R
import kotlinx.android.synthetic.main.fragment_final.*

class FinalFragment : Fragment(R.layout.fragment_final) {

    val arguments : FinalFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = arguments.userName
        val password = arguments.password

        tvResult.setText("You've successfully logged in with UserName : $userName & Password: $password")

        buttonGoHome.setOnClickListener {
            val action = FinalFragmentDirections.actionFinalFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}