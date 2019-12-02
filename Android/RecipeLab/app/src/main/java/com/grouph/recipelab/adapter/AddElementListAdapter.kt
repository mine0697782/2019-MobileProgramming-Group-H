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
import kotlinx.android.synthetic.main.item_element_add.view.*

class AddElementListAdapter(val data: ArrayList<ArrayList<String>>, val context: Activity) : RecyclerView.Adapter<AddElementListAdapter.ViewHolder>() {

    private val TAG = "AddElementListAdapter"

    val mDataset = arrayOf<Array<String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_element_add, parent, false)

        return ViewHolder(view, MyCustomEditTextListener())
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind()
    }


    inner class ViewHolder(v: View, listener: MyCustomEditTextListener) : RecyclerView.ViewHolder(v){
        val view = v
        val myListener = listener
        fun bind() {
            view.edit_element_name.addTextChangedListener(myListener)
        }
    }

    inner class MyCustomEditTextListener() : TextWatcher {
        private var col: Int = 0
        private var raw: Int = 0

        fun updatePosition(col: Int, raw: Int) {
            this.col = col
            this.raw = raw
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
//            mDataset[0][col] = charSequence.toString()
            Log.d(TAG, "col : "+col+" / "+charSequence.toString())
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }
}