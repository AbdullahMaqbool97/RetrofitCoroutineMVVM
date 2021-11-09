package com.example.samplecoroutinetask.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.listAdapter
import com.example.samplecoroutinetask.interface_.onClick_details
import com.example.samplecoroutinetask.model.Items
import com.example.samplecoroutinetask.model.Mainviewmodel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_drawer_menu.*
import kotlinx.android.synthetic.main.layout_main.*
import org.json.JSONObject
import java.lang.StringBuilder


class MainActivity : AppCompatActivity(), onClick_details {
    lateinit var viewModel: Mainviewmodel
    private val listAdapter = listAdapter(arrayListOf())
    private var mList: List<Items> = ArrayList()

    lateinit var navController: NavController

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navController = Navigation.findNavController(layout_main)

        viewModel = ViewModelProviders.of(this).get(Mainviewmodel::class.java)
        viewModel.refresh()
        rv_main.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        observeViewModel()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        toolbar.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        drawerAboutUs.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))

//            Navigation.findNavController(it).navigate(R.id.about_activity)
        }

        about_us_icon.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))

//            Navigation.findNavController(it).navigate(R.id.about_activity)
        }

        about_us_txt.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))

//            Navigation.findNavController().navigate(R.id.about_activity)
        }

        logout_txt.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.Items.observe(this, Observer { countries ->
            countries?.let {
                rv_main.visibility = View.VISIBLE
                mList = it
                listAdapter.updateItems(it, this)
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPosition(position: Int) {
        val itemAdapter: Items = mList[position]

        val login: String = itemAdapter.owner.getValue("login").toString()
        val id: String = itemAdapter.owner.getValue("id").toString()
        val type: String = itemAdapter.owner.getValue("type").toString()
        val url: String = itemAdapter.owner.getValue("url").toString()

        val intent = Intent(applicationContext, DetailsActivity::class.java)
        intent.putExtra("login", login)
        intent.putExtra("id", id)
        intent.putExtra("type", type)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}
