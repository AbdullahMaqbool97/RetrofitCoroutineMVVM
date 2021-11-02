package com.example.samplecoroutinetask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecoroutinetask.Model.Items
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.listAdapter.ViewHolder
import kotlinx.android.synthetic.main.itemcustom.view.*

class listAdapter(var items: ArrayList<Items>) : RecyclerView.Adapter<ViewHolder>() {

    fun updateItems(newItems: List<Items>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.itemcustom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val texta = view.texta
        private val textb = view.textb
        private val textc = view.textc
        private val textd = view.textd
        private val texte = view.texte

        fun bind(items: Items) {
            texta.text = items.id.toString()
            textb.text = items.node_id
            textc.text = items.name
            textd.text = items.full_name
            texte.text = items.private.toString()
        }
    }
}