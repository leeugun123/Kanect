package org.techtown.kanect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.techtown.kanect.Fragment.ListFragment
import org.techtown.kanect.Fragment.PostFragment
import org.techtown.kanect.Fragment.TalkFragment
import org.techtown.kanect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNaviInit()

    }

    private fun bottomNaviInit() {

        supportFragmentManager.beginTransaction().add(R.id.fragment_container,ListFragment()).commit()

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.navigation_list -> {

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    ListFragment()).commitAllowingStateLoss()

                return true;
            }
            R.id.navigation_talk -> {

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    TalkFragment()).commitAllowingStateLoss()

                return true;

            }
            R.id.navigation_post -> {

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    PostFragment()).commitAllowingStateLoss()

                return true;

            }

        }

        return false

    }


}