/**
 *  작성자 : 최영준
 */

package com.grouph.recipelab.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.activity.ResearchingListActivity
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Element
import com.grouph.recipelab.model.Research
import kotlinx.android.synthetic.main.activity_researching_list.view.*
import kotlinx.android.synthetic.main.item_research_elements.view.*
import kotlinx.android.synthetic.main.item_research_list_card.view.*
import java.lang.Exception

class ResearchListAdapter(
    val data: ArrayList<Research>,
    val recipeNo: Int,
    val eleNum: Int,
    val context: Activity,
    val helper: MySQLIteOpenHelper
) : RecyclerView.Adapter<ResearchListAdapter.ViewHolder>() {

    private val TAG = "ResearchListAdapter"

    private val db = helper.readableDatabase
    private val units = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_research_elements, parent, false)
        units.addAll(getUnits(recipeNo))
        Log.d(TAG, units.toString())

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            bind(item, getValues(recipeNo,item.researchNo), units)
        }
    }

    fun getUnits(recipeNo: Int): ArrayList<String> {
        val data = arrayListOf<String>()
        try {
            val cursor =
                db.rawQuery("select unit from elementsTable where researchNo=-1 and recipeNo="+recipeNo, null)
            while (cursor.moveToNext()) {
                data.add(cursor.getString(0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    fun getNames(recipeNo: Int): ArrayList<String> {
        val data = arrayListOf<String>()
        try {
            val cursor =
                db.rawQuery("select name from elementsTable where researchNo=-1 and recipeNo="+recipeNo, null)
            while (cursor.moveToNext()) {
                data.add(cursor.getString(0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    fun getValues(recipeNo: Int, researchNo: Int): ArrayList<String> {
        val data = arrayListOf<String>()
        try {
            val cursor =
                db.rawQuery(
                    "select value from elementsTable where researchNo="+researchNo+" and recipeNo=" + recipeNo,
                    null
                )
            while (cursor.moveToNext()) {
                data.add(cursor.getString(0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: Research, values: ArrayList<String>, units: ArrayList<String>) {
            var data = arrayListOf<String>()

            view.text_research_date.text = item.date
            view.text_score.text = item.score.toString()
            view.text_keyname1.text = values[0] + units[0]
            view.text_keyname2.text = values[1] + units[1]
            view.text_keyname3.text = values[2] + units[2]

//            view.setOnClickListener(listener)
//            view.text_research_coffee_name.text = item.recipeName
//            view.text_num_research.text = item.resNum.toString()
//            view.text_research_coffee_date.text = item.currentDate
        }
    }
}