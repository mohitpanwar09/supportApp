package com.example.supportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.system.Os.open
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_kt.*

class MainActivityKt : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kt)

        setSupportActionBar(findViewById(R.id.toolbar))


        val drawerToggle = ActionBarDrawerToggle(this, drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser==null){
            val intent =Intent(this,MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id._myProfile->{
                Toast.makeText(this,"My Profile",Toast.LENGTH_SHORT).show()
            }

         ///////// review category ///////
            R.id._family->{
                Toast.makeText(this,"Family section",Toast.LENGTH_SHORT).show()
            }
            R.id._friends->{
                Toast.makeText(this,"Friends section",Toast.LENGTH_SHORT).show()
            }
            R.id._relationships->{
                Toast.makeText(this,"relationShips section",Toast.LENGTH_SHORT).show()
            }
            R.id._single_hood->{
                Toast.makeText(this,"singleHood",Toast.LENGTH_SHORT).show()
            }
            R.id._solo_trip_experiences->{
                Toast.makeText(this,"solo Trip",Toast.LENGTH_SHORT).show()
            }
            R.id._customer_service->{
                Toast.makeText(this,"customer",Toast.LENGTH_SHORT).show()
            }
            R.id._Indian_relatives->{
                Toast.makeText(this,"indian",Toast.LENGTH_SHORT).show()
            }
            R.id._Jobs->{
                Toast.makeText(this,"job",Toast.LENGTH_SHORT).show()
            }
            R.id._food_outlets->{
                Toast.makeText(this,"food outlet",Toast.LENGTH_SHORT).show()
            }

            R.id._logout->{
                Toast.makeText(this,"logout",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                val intent=Intent(this,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id._about->{
                Toast.makeText(this,"About",Toast.LENGTH_LONG).show()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}