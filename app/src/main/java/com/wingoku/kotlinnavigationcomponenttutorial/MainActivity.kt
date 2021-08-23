package com.wingoku.kotlinnavigationcomponenttutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

//learned from CODING IN FLOW channel: https://www.youtube.com/watch?v=WWgNCPu8MeQ&list=PLrnPJCHvNZuCamMFswP597mUF-whXoAA6&index=6
/*
    <androidx.fragment.app.FragmentContainerView
        android:name="androidx.navigation.fragment.NavHostFragment" //NO.1
        app:defaultNavHost="true"                                   //NO.2
        app:navGraph="@navigation/nav_graph" />                     //NO.3

    NO.1:
        This tells Android what kind of NavHostFragment we want to use. This contains all the logic
        how to navigate among different fragments mentioned in the navigation Graph.
        In most cases we use the default NavHostFragment (androidx.navigation.fragment.NavHostFragment)
        for FragmentContainerView.

    NO.2
        This will let NavHostFragment intercept the back button in toolbar/android's back button
        without this we'd have to write our own logic to intercept the back button and that defeats
        the purpose of using NavigationComponent!!

    NO.3:
        This is important to tell FragmentContainerView which navgiation graph to use. Without this
        Navigation Component won't work!

   _____________________________________________________________________________________________________

   <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragments.HomeFragment" // NO.1
    >

   NO.1:
        This is needed to show the layout of the fragment inside the nav_graph when we add the fragment
        in the navGraph. Without it, the layout of the added fragment won't be visible in the nav_graph

   _____________________________________________________________________________________________________

   NAV_GRAPH FRAGMENT:

       <fragment
        android:id="@+id/finalFragment"
        android:name="com.wingoku.kotlinnavigationcomponenttutorial.fragments.FinalFragment"
        android:label="fragment_final"
        tools:layout="@layout/fragment_final" >
        <action
            android:id="@+id/action_finalFragment_to_homeFragment"
            app:destination="@id/homeFragment"              //NO.1
            app:popUpTo="@id/homeFragment"                  //NO.2
            app:popUpToInclusive="true"                     //NO.3
            />
        <argument                                           //NO.4
            android:name="userName"
            app:argType="string" />
        <argument
            android:name="password"
            app:argType="string" />
    </fragment>

   NO.1:
        app:destination value in nav_graph tells navController which fragment to go to
        when an action is performed Eg. moving to homeFragment when the user taps "Go Home" button
        in the Final Fragment in this project

   NO.2:
        app:popUpTo="@id/homeFragment"  value inside nav_graph tells navController to remove/pop all the fragments
        on backStack that're in between finalFragment and homeFragment.
        i-e; in our project the only fragment between finalFragment and homeFragment is the loginFragment
        so when the user taps on "Go Home" button in finalFragment and navController goes to HomeFragment,
        it pops the the loginFragment from backStack

   NO.3:
        app:popUpToInclusive="true"  tells the navController that if the destination fragment
        is already in the backStack, remove that as well since app:destination always creates a new
        object for the destination fragment and pushes it on to the backStack

        Eg. if we don't do this in this project, when the user pressed "Go Home" button in the finalFragment
        the backStack will look something like this:

        BackStack: HomeFragment -> LoginFragment -> FinalFragment => User Taps "Go Home Button"
        BackStack: HomeFragment(old one) -> HomeFragment (the one that opened up when user tapped "Go Home")
            So when the user presses back button on android navigation bar, it'll pop current HomeFragment
            and show the old HomeFragment, it's not the desired behavior.

            Therefore we popUpToInclusive tells navController, if the destination fragment is already on
            the backstack, remove that as well from backStack

    NO.4:
        When a fragment declares arguments in the nav_graph, any fragment that wants to go
        to that fragment (fragment with argument) will have to provide arguments in action. Look LoginFragment.kt file

        _____________________________________________________________________________________________________

        app_menu.xml
      <item
        android:id="@+id/settingsFragment"      //NO. 1
        android:title="Settings"
        android:menuCategory="secondary"        //NO.2
        app:showAsAction="never"
        />

        NO.1:
            The id of the menu item and the item of the fragment inside nav_graph MUST MATCH!!!
            otherwise navController won't be able to navigate to this fragment when this menu item is clicked

        NO.2:
            When the menu item is selected, upon pressing back button it always takes us back to the VERY first
            fragment in the stack rather the fragment that's previously opened. If we don't want
            such behavior we need to make this menu item SECONDARY

        _____________________________________________________________________________________________________

      nav_graph.xml

      <action
        android:id="@+id/action_global_myGlobalActionFragment"
        app:destination="@id/myGlobalActionFragment"        //NO.1
        />

       This is how we can declare fragments that we want to access from anywhere inside the app. They're
       called Global Actions. We can add the fragment in the nav_graph and then right click on that
       fragment and then Add Action -> Global

       We recompile the project and than we get the direction/action for that fragment inside
       NavGraphDirections generated class.

       To read about its USE CASE, go to MyGlobalActionFragment and read the comments
 */

class MainActivity : AppCompatActivity() {

    //this controls navigation
    //doing lateinit cuz we don't have value for navController until onCreate() of the activity is called
    private lateinit var navigationController : NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //finding and initializing navHostFragment which is a container for other fragments
        //we want to show in the main activity
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentHostContainer) as NavHostFragment
        navigationController = navHostFragment.findNavController()

        //we need to pass TOP LEVEL FRAGMENTS in this method otherwise when we switch among
        //different fragments using bottomNavigationView, we'll see Back button in toolbar
        //video: https://www.youtube.com/watch?v=llWsm9Pjkpc&list=PLrnPJCHvNZuCamMFswP597mUF-whXoAA6&index=7
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.searchFragment))

        //we have to do this cuz we're using a custom toolbar
        setSupportActionBar(toolbar)

        //we do this so that the toolbar can show back/up button and animate those buttons when
        //we move from one fragment to another. NavController sets the back/up buttons on the toolbar automatically
        setupActionBarWithNavController(navigationController, appBarConfiguration)

        //THIS IS IMPORTANT!! IF WE DON'T PASS NAV CONTROLLER TO BottomNavigationView,
        //when we click on bottomNavigationView items, it WON'T DO ANYTHING!
        //all the fragments that we want to show in bottomNavView must be added in the menu file
        //and those fragments must also be added to nav_graph.xml
        bottomNavigationView.setupWithNavController(navigationController)
    }

    //this method is required to handle back/up navigation using toolbar back button.
    //we need to call navigationController.navigateUp() inside this method to tell navController
    //user pressed a back button in TOOLBAR only (android back button still works) and we need to pop current visible fragment off the stack!
    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true //without true it won't get inflated
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            //NOTE: ID OF MENU ITEM AND ID OF THE FRAGMENT IN NAV_GRAPH MUST BE SIMILAR OTHERWISE NAV CONTROLLER WON'T
            //OPEN THE FRAGMENT WHEN THE MENU ITEM IS SELECTED!!!!!!!
            R.id.myGlobalActionFragment -> {
                //following is the way to navigate to a Global Action Fragment!!!
                val action = NavGraphDirections.actionGlobalMyGlobalActionFragment()
                navigationController.navigate(action)
                true
            }
            else -> {
                println("ïtem clicked ${item.itemId} ")
                item.onNavDestinationSelected(navigationController) || super.onOptionsItemSelected(item)
            }
        }
    }
}