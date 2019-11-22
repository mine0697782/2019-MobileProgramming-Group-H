/**
 *  작성자 : 정민재
 */

package com.grouph.recipelab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_research_list_card.view.*

class ResearchingListAdapter(val data: ArrayList<String>, val context: Context, val layout: Int)
    : RecyclerView.Adapter<ResearchingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val listener = View.OnClickListener {
            Toast.makeText(context, item+" clicked", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(item, listener)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: String, listener: View.OnClickListener) {
            view.setOnClickListener(listener)
            view.text_research_coffee_name.text = item
        }
    }
}