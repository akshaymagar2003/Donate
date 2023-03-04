package com.example.loginapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donate.MainActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoggedInBinding
import com.example.donate.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoggedIn : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var binding:ActivityLoggedInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")
        binding.logout.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(isLogin=="1")
        {
            var email=intent.getStringExtra("email")
            if(email!=null)
            {
                setText(email)
                with(sharedPref.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }
            else{
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else
        {
            setText(isLogin)
        }

    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    binding.name.text=tasks.get("Name").toString()
                    binding.phone.text=tasks.get("Phone").toString()
                    binding.emailLog.text=tasks.get("email").toString()

                }
        }

    }
}