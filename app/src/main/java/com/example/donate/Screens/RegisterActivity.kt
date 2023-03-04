package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.donate.MainActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityMainBinding
import com.example.donate.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        binding.Continue.setOnClickListener {
            if(checking())
            {
                var email=binding.EmailRegister.text.toString()
                var password= binding.PasswordRegister.text.toString()
                var name=binding.Name.text.toString()
                var phone=binding.Phone.text.toString()
                val user= hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "email" to email
                )
                val Users=db.collection("USERS")
                val query =Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener {
                            tasks->
                        if(tasks.isEmpty)
                        {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful)
                                    {
                                        Users.document(email).set(user)
                                        val intent=Intent(this, LoggedIn::class.java)
                                        intent.putExtra("email",email)

                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this,"Authentication Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                        else
                        {
                            Toast.makeText(this,"User Already Registered", Toast.LENGTH_LONG).show()
                            val intent= Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }
            else{
                Toast.makeText(this,"Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun checking():Boolean{
        if(binding.Name.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.Phone.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.EmailRegister.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.PasswordRegister.text.toString().trim{it<=' '}.isNotEmpty()
        )
        {
            return true
        }
        return false
    }
}