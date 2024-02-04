package com.example.donate.Screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.donate.Forms.*
import com.example.donate.MainActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoggedInBinding
import com.google.firebase.firestore.FirebaseFirestore


class LoggedIn : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var navController: NavController
    lateinit var binding : ActivityLoggedInBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false


     binding.fab.setOnClickListener {
         replaceFragment(AddItemFragment())
     }
        replaceFragment(ItemViewFragment())
//
//
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(ItemViewFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.placeholder->replaceFragment(AddItemFragment())
            }
            true
        }



        binding.apply {
            toggle = ActionBarDrawerToggle(this@LoggedIn, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        Toast.makeText(this@LoggedIn, "Home Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.feedback -> {
                        Toast.makeText(this@LoggedIn, "Feed back Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.help-> {
                        Toast.makeText(this@LoggedIn, "Help Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.aboutus->{
                        Toast.makeText(this@LoggedIn,"about Us clicked",Toast.LENGTH_SHORT).show()
                    }
                    R.id.logout->{
                       Logout()

                    }
                }
                true
            }

        }
        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

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
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else
        {
            setText(isLogin)
        }




    }



    private fun Logout() {
        val sharedPref= this.getPreferences(Context.MODE_PRIVATE) ?:return
        sharedPref.edit().remove("Email").apply()
        var intent = Intent(this@LoggedIn, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setText(email:String?)
    {

        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    binding.Username.text=tasks.get("Name").toString()
//                    binding.phone.text=tasks.get("Phone").toString()
//                    binding.emailLog.text=tasks.get("email").toString()
//                    binding.name.text =tasks.get("Name").toString()

                }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close()
        } else super.onBackPressed()

    }
    }