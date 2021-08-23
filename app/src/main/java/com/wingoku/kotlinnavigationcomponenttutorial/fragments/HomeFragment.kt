package com.wingoku.kotlinnavigationcomponenttutorial.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wingoku.kotlinnavigationcomponenttutorial.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: inside")

        buttonStart.setOnClickListener{
            //moving from one fragment to another is called ACTION
            //when we create links in Nav_Graph from one fragment to another,
            //it generates actions. In order to get the actions for a particular fragment
            //we need to rebuild the project and Android Studio will create
            //FRAGMENT-NAMEDirections().whateverActionHere()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()

            //navigating using actions uses Navigation Component's SAFE ARGS plugin
            //it means if the destination fragment requires any arguments and we forget
            //to pass those arguments before navigating to destination fragment, THE PROJECT WON'T compile
            //we can also use findNavController().navigate(R.id.loginFragment) but it won't give us safety for the reason mentioned above
            findNavController().navigate(action)
        }
    }
}