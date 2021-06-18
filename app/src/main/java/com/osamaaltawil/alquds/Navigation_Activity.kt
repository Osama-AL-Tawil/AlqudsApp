package com.osamaaltawil.alquds

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.osamaaltawil.alquds.Adapters.Function
import com.osamaaltawil.alquds.Adapters.Items_Adapter
import kotlinx.android.synthetic.main.activity_navigation_.*

class Navigation_Activity : AppCompatActivity() {
    var HomeFragment: Fragment = HomeFragment()
    var LandmarkFragment: Fragment = LandmarkFragment()
    var InformationFragment: Fragment = InformationsFragment()
    var activeFragment = HomeFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.nav_home -> {
                moveToFragment(HomeFragment,activeFragment)
                changeActionTitle(resources.getString(R.string.tv_home))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_landMarks -> {
                moveToFragment(LandmarkFragment,activeFragment)
                changeActionTitle(resources.getString(R.string.tv_landmarks))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_information -> {
                moveToFragment(InformationFragment,activeFragment)
                changeActionTitle(resources.getString(R.string.tv_informations))
                return@OnNavigationItemSelectedListener true
            }

        }

    false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_)
        changeActionTitle(resources.getString(R.string.tv_home))
        //start Fragment
        supportFragmentManager.beginTransaction().add(R.id.con_main,HomeFragment).commit()

       bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)






    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //onRetainNonConfigurationInstance()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        if (activeFragment == HomeFragment) {
//            removeFragment(HomeFragment)
//        } else if (activeFragment == LandmarkFragment) {
//            removeFragment(LandmarkFragment)
//        } else if (activeFragment == InformationFragment) {
//            removeFragment(InformationFragment)
//        }


    }
     //create option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saved->startActivity(Intent(this,Favorite_Activity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

   // -----------------------------------------------------------------------------------------------

    //move between Fragment Function using Button Navigation
    fun moveToFragment(fragment: Fragment, active: Fragment){
        //Get current fragment placed in container
        if (fragment.isAdded){
            supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
            activeFragment=fragment

        }else{
            supportFragmentManager.beginTransaction().add(R.id.con_main,fragment).commit()
            supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
            activeFragment=fragment
        }

    }


    fun changeActionTitle(new_title:String){
        supportActionBar!!.title=new_title
    }
//    fun removeFragment(newFragment: Fragment){
//        //Get current fragment placed in container
//        supportFragmentManager.beginTransaction().remove(activeFragment).commit()
//        supportFragmentManager.beginTransaction().remove(newFragment).commit()
//        supportFragmentManager.beginTransaction().add(R.id.con_main,newFragment).commit()
//    }
}