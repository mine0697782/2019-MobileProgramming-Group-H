/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.activity.ResearchingListActivity
import com.grouph.recipelab.activity.TestActivity
import com.grouph.recipelab.model.Recipe
import kotlinx.android.synthetic.main.item_research_list_card.view.*

class ResearchingListAdapter(val data: ArrayList<Recipe>, val context: Activity, val layout: Int)
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
//            Toast.makeText(context, item.recipeName+" clicked", Toast.LENGTH_SHORT).show()
//            context.startActivity(Intent(context, ResearchingListActivity::class.java))
            context.startActivityForResult(Intent(context, /*ResearchingListActivity*/TestActivity::class.java), 0)
        }
        holder.apply {
            bind(item, listener)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: Recipe, listener: View.OnClickListener) {
            view.setOnClickListener(listener)
            view.text_research_coffee_name.text = item.recipeName
            view.text_num_research.text = item.resNum.toString()
        }
    }
}