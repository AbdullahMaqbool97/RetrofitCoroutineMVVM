package com.example.samplecoroutinetask.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplecoroutinetask.Model.Mainviewmodel
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.listAdapter
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_drawer_menu.*
import kotlinx.android.synthetic.main.layout_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: Mainviewmodel
    private val listAdapter = listAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(Mainviewmodel::class.java)
        viewModel.refresh()
        rv_main.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        observeViewModel()

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

        drawerMoreApp.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))
        }

        more_app_icon.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))
        }

        more_app_txt.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            startActivity(Intent(applicationContext, AboutusActivity::class.java))
        }

    }

    private fun observeViewModel() {
        viewModel.Items.observe(this, Observer { countries ->
            countries?.let {
                rv_main.visibility = View.VISIBLE
                listAdapter.updateItems(it)
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
}