package com.example.donate.Screens
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController

import com.example.donate.Forms.ItemViewFragment
import com.example.donate.MainActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoggedInBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class LoggedIn : AppCompatActivity(){
    private lateinit var db: FirebaseFirestore
    private lateinit var binding:ActivityLoggedInBinding
    private lateinit var navController: NavController

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

//       val AddItemFragment=AddItemFragment()
//        val fm:FragmentManager=supportFragmentManager
//fm.beginTransaction().add(R.id.drawerLayout,AddItemFragment).commit()






        val itemViewFragment =ItemViewFragment()
        val fm:FragmentManager =supportFragmentManager
        fm.beginTransaction().add(R.id.drawerLayout,itemViewFragment).commit()

//        binding.floatingActionButton.setOnClickListener{view->
//            val intent=Intent(this, DonateFormActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
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
        actionBarDrawerToggle=ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        binding.logout.setOnClickListener {
//            sharedPref.edit().remove("Email").apply()
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->Toast.makeText(this,"Rate Us",Toast.LENGTH_SHORT).show()

//                R.id.logout->{
//
//                }
            }
            true
        }

    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
//                    binding.name.text=tasks.get("Name").toString()
//                    binding.phone.text=tasks.get("Phone").toString()
//                    binding.emailLog.text=tasks.get("email").toString()
//                    binding.name.text =tasks.get("Name").toString()

                }
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle!!.onOptionsItemSelected(item)){true}
      else  super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.close()
        }
        else super.onBackPressed()
    }
}