/**
 * 작성자 : 정민재
 */

package com.grouph.recipelab.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.model.Element
import kotlinx.android.synthetic.main.element_add_list.view.*
import kotlinx.android.synthetic.main.item_element_add.view.*
import kotlinx.android.synthetic.main.item_element_add_research.view.*

class AddResearchAdapter2(val data: ArrayList<Element>) : RecyclerView.Adapter<AddResearchAdapter2.ViewHolder>() {

    private val TAG = "AddElementListAdapter"

    var mDataset = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_element_add_research, parent, false)

        return ViewHolder(view, MyCustomEditTextListener())
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listener.updatePosition(holder.adapterPosition)
        holder.editDegree.setText(mDataset[holder.adapterPosition])
        holder.textUnit.text = data[holder.adapterPosition].unit
        holder.textName.text = data[holder.adapterPosition].name
    }

    inner class ViewHolder(v: View, val listener: MyCustomEditTextListener): RecyclerView.ViewHolder(v) {
        val view = v
        var textName: TextView
        var editDegree: EditText
        var textUnit: TextView

        init {
            textName = view.text_element_name
            editDegree = view.edit_element_degree
            textUnit = view.text_element_unit
            editDegree.addTextChangedListener(listener)
        }
    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position: Int = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            mDataset[position] = charSequence.toString()
            Log.d(TAG, "position["+position+"] / "+charSequence.toString())
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }
}