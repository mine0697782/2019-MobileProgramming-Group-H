package com.grouph.recipelab.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.grouph.recipelab.model.Element
import kotlinx.android.synthetic.main.item_element_add.view.*

class AddElementListAdapter(var data: ArrayList<ArrayList<String>>, val context: Activity) : RecyclerView.Adapter<AddElementListAdapter.ViewHolder>() {

    private val TAG = "AddElementListAdapter"

    var mDataset = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_element_add, parent, false)

        return ViewHolder(view, MyCustomEditTextListener(0), MyCustomEditTextListener(1))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameListener.updatePosition(holder.adapterPosition)
        holder.unitListener.updatePosition(holder.adapterPosition)
        holder.editName.setText(mDataset[holder.adapterPosition][0])
        holder.editUnit.setText(mDataset[holder.adapterPosition][1])
        holder.textNum.text = (holder.adapterPosition+1).toString()+"번"
    }

    inner class ViewHolder(v: View, val nameListener: MyCustomEditTextListener, val unitListener: MyCustomEditTextListener) : RecyclerView.ViewHolder(v){
        val view = v
        var editName: EditText
        var editUnit: EditText
        var textNum: TextView

        init {
            textNum = view.text_element_num
            editName = view.edit_element_name
            editName.addTextChangedListener(nameListener)
            editUnit = view.edit_element_unit
            editUnit.addTextChangedListener(unitListener)
        }

    }

    inner class MyCustomEditTextListener(val idx: Int) : TextWatcher {
        private var position: Int = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            mDataset[position][idx] = charSequence.toString()
            Log.d(TAG, "position["+position+"]["+idx+"] / "+charSequence.toString())
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }
}