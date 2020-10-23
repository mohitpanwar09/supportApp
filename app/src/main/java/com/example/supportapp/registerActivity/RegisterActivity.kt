package com.example.supportapp.registerActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.supportapp.R
import com.example.supportapp.ktClass.AddUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        register_button.setOnClickListener {

                if(register_userEmail.text.isEmpty()){
                    register_userEmail.error="Can't be Empty"
                    register_userEmail.requestFocus()
                    return@setOnClickListener
                }
            if(register_userName.text.isEmpty()){
                register_userName.error="Can't be Empty"
                register_userName.requestFocus()
                return@setOnClickListener
            }
            getUserStored()
        }
    }

    private fun getUserStored(){
        val userName=register_userName.text.toString()
        val userEmail=register_userEmail.text.toString()
        val userUid=FirebaseAuth.getInstance().uid
        val userDetails=AddUser(userName,userEmail)
        FirebaseDatabase.getInstance().getReference("User/$userUid").setValue(userDetails)
                .addOnCompleteListener {
                    Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to Registered",Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"Check Internet Connection!!",Toast.LENGTH_SHORT).show()
                }
    }
}