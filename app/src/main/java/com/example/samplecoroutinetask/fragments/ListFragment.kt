package com.example.samplecoroutinetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.ListAdapter
import com.example.samplecoroutinetask.interface_.onClick_details
import com.example.samplecoroutinetask.model.Items
import com.example.samplecoroutinetask.model.Mainviewmodel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), onClick_details {
    lateinit var viewModel: Mainviewmodel
    private val listAdapter = ListAdapter(arrayListOf())
    private var mList: List<Items> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(Mainviewmodel::class.java)
        viewModel.refresh()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.Items.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                recyclerView.visibility = View.VISIBLE
                mList = it
                listAdapter.updateItems(it, this)
            }
        })
    }

    override fun onPosition(position: Int) {
        val itemAdapter: Items = mList[position]

        val login: String = itemAdapter.owner.getValue("login").toString()
        val id: String = itemAdapter.owner.getValue("id").toString()
        val type: String = itemAdapter.owner.getValue("type").toString()
        val url: String = itemAdapter.owner.getValue("url").toString()

        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putString("login", login)
        bundle.putString("id", id)
        bundle.putString("type", type)
        bundle.putString("url", url)
        fragment.arguments = bundle

        Navigation.findNavController(requireView()).navigate(R.id.action_listFragment_to_detailsFragment, bundle)
    }
}