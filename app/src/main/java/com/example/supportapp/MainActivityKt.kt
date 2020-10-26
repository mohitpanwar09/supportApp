package com.example.supportapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.system.Os.close
import android.system.Os.open
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main_kt.*
import java.util.*

class MainActivityKt : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val readImage=569
    private var postUrl: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kt)

        setSupportActionBar(findViewById(R.id.toolbar))


        val drawerToggle = ActionBarDrawerToggle(this, drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        main_post_addImage.setOnClickListener {
            checkForPermission()
        }

        main_post_send.setOnClickListener {

        }


    }

    private fun fetchFromGalary(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,0)
    }
    private var selectedImgUri: Uri?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode==0 && resultCode==Activity.RESULT_OK && data!=null){
                    toast("Photo selected")
                    selectedImgUri=data.data
                val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,selectedImgUri)
                main_post_addImage.setImageBitmap(bitmap)

                storeToFirebase()
            }else{
                toast("Enable to select image")
            }
    }

    private fun storeToFirebase(){
            if(selectedImgUri==null) return
        val fileName=FirebaseAuth.getInstance().uid.toString()+UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/image/$fileName")
                ref.putFile(selectedImgUri!!)
                        .addOnSuccessListener {
                                toast("stored to firebase")
                            ref.downloadUrl.addOnSuccessListener {
                                postUrl=it.toString()
                            }
                        }

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


    private fun checkForPermission(){
            if(Build.VERSION.SDK_INT>=23){
                if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),readImage)
                    return
                }
                fetchFromGalary()
            }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when(requestCode){
            readImage->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchFromGalary()
                }else{
                    Toast.makeText(this, "PERMISSION IS NOT GRANTED", Toast.LENGTH_SHORT).show()
                }
            }
            else->super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}