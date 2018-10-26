package io.armcha.sampleapp

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.armcha.sampleapp.data.DataSource
import kotlinx.android.synthetic.main.item_view.view.*

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    private val items = DataSource.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(imageUrl: String) {
            Picasso.get()
                    .load(imageUrl)
                    .into(itemView.imageView)
            itemView.nameText.text = "Item $adapterPosition"
            itemView.setOnClickListener {

            }
        }
    }
}

infix fun RecyclerView.setUpWith(simpleAdapter: SimpleAdapter) {
    adapter = simpleAdapter
    layoutManager = LinearLayoutManager(context)
}


