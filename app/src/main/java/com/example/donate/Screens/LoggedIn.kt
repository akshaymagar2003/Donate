package com.example.donate.Screens
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.donate.Forms.ItemViewFragment
import com.example.donate.MainActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoggedInBinding
import com.google.firebase.firestore.FirebaseFirestore


class LoggedIn : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    lateinit var binding : ActivityLoggedInBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            toggle = ActionBarDrawerToggle(this@LoggedIn, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.firstItem -> {
                        Toast.makeText(this@LoggedIn, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.secondtItem -> {
                        Toast.makeText(this@LoggedIn, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.thirdItem -> {
                        Toast.makeText(this@LoggedIn, "third Item Clicked", Toast.LENGTH_SHORT).show()
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

    private fun setText(email:String?)
    {   val itemViewFragment =ItemViewFragment()
        val fm:FragmentManager =supportFragmentManager
        fm.beginTransaction().add(R.id.drawerLayout,itemViewFragment).commit()

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
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}