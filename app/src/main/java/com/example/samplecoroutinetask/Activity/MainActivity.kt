package com.example.samplecoroutinetask.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplecoroutinetask.Model.Mainviewmodel
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.listAdapter
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    private fun observeViewModel() {
        viewModel.Items.observe(this, Observer {countries ->
            countries?.let {
                rv_main.visibility = View.VISIBLE
                listAdapter.updateItems(it) }
        })
    }
}