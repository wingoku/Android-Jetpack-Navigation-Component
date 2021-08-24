package com.wingoku.kotlinnavigationcomponenttutorial.fragments

import androidx.fragment.app.Fragment
import com.wingoku.kotlinnavigationcomponenttutorial.R
import kotlinx.android.synthetic.main.fragment_final.*

//this is just a dummy fragment to learn Global Actions in NavGraph.
//Fragments that're called using Global Actions can be called anywhere from the app
/*
Example of Usecase:

Basically the app I'm creating has a large number of different fragments. Most of the fragments
talk to a backend and when they do they can discover that their session has timed out. When this
occurs I want to go to the login-fragment. The only way I've been able to do this is to create an
action for every fragment with the destination pointing to the login screen. This is a lot of boiler
plate that I would rather avoid. Is there a simpler way to do this?

https://stackoverflow.com/questions/52937257/jetpack-navigation-to-a-common-destination/52968906
 */
class MyGlobalActionFragment : Fragment(R.layout.fragment_settings) {

}