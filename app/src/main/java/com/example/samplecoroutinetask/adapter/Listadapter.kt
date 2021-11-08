package com.example.samplecoroutinetask.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecoroutinetask.Interface.onClick_details
import com.example.samplecoroutinetask.Model.Items
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.adapter.listAdapter.ViewHolder
import kotlinx.android.synthetic.main.itemcustom.view.*

class listAdapter(var items: ArrayList<Items>) : RecyclerView.Adapter<ViewHolder>() {

    private var onitemclick: onClick_details? = null

    fun updateItems(newItems: List<Items>, onitemClick: onClick_details) {
        items.clear()
        items.addAll(newItems)
        onitemclick = onitemClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.itemcustom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.card.setOnClickListener(View.OnClickListener {
            onitemclick?.onPosition(position)
            Log.d("Valueiii", "onBindViewHolder: " + items[position].owner.getValue("id").toString())
           /* val login: String = items[position].owner.getValue("login").toString()
            val id: String = items[position].owner.getValue("id").toString()
            val type: String = items[position].owner.getValue("type").toString()
            val url: String = items[position].owner.getValue("url").toString()

            val intent = Intent(it.context, DetailsActivity::class.java)
            intent.putExtra("login", login)
            intent.putExtra("id", id)
            intent.putExtra("type", type)
            intent.putExtra("url", url)
            it.context.startActivity(intent)*/

//            Navigation.findNavController(it).navigate(R.id.about_activity1)
        })
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
        val card = view.card

        fun bind(items: Items) {
            texta.text = "Id: \t" + items.id.toString()
            textb.text = "Node Id: \t" + items.node_id
            textc.text = "Name: \t" + items.name
            textd.text = "FullName: \t" + items.full_name
            texte.text = "Private: \t" + items.private.toString()

            Log.d("Valueit", "bind: " + items.owner.getValue("login").toString())
        }
    }
}