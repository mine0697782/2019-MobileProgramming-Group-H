package com.grouph.recipelab.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import kotlin.collections.ArrayList

class AddResearchAdapter(context: Context, data:ArrayList<ArrayList<String>>) :RecyclerView.Adapter<AddResearchAdapter.mViewHolder>() {

    var data = data
    var context= context
    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.EleName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.toFloat())
        holder.EleMany.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.toFloat())
        holder.EleDan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.toFloat())

        holder.EleName.gravity = Gravity.CENTER
        holder.EleMany.gravity = Gravity.CENTER
        holder.EleDan.gravity = Gravity.CENTER

        holder.EleName.setText(data.get(position)[0])
        holder.EleMany.setText(data.get(position)[1])
        holder.EleDan.setText(data.get(position)[2])
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.ele_list, parent, false)
        var holder:mViewHolder = mViewHolder(view)
        return holder
    }


    inner class mViewHolder : RecyclerView.ViewHolder , View.OnCreateContextMenuListener{
        var EleName : TextView
        var EleMany : TextView
        var EleDan: TextView
        constructor(view:View) : super(view) {
            this.EleName = view.findViewById(R.id.Name_listitem)
            this.EleMany = view.findViewById(R.id.Many_listitem)
            this.EleDan = view.findViewById(R.id.Dan_listitem)

            view.setOnCreateContextMenuListener(this)
        }


        override fun onCreateContextMenu(menu:ContextMenu?, v:View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            var Edit: MenuItem = menu!!.add(Menu.NONE, 1001, 1, "편집")
            var Delete: MenuItem = menu!!.add(Menu.NONE, 1002, 2, "삭제")
            Edit.setOnMenuItemClickListener(onEditMenu)
            Delete.setOnMenuItemClickListener(onEditMenu)
        }


        val onEditMenu:MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener{
            item: MenuItem ->
            when (item.itemId) {
                1001 -> {
                    var builder:AlertDialog.Builder = AlertDialog.Builder(context)
                    var view : View = LayoutInflater.from(context)
                        .inflate(R.layout.element_add_list, null, false)
                    builder.setView(view)


                    var btnEdit : Button = view.findViewById(R.id.btnEdit)
                    var editElename: EditText = view.findViewById(R.id.editEleName)
                    var editEleDan: EditText = view.findViewById(R.id.editEleDan)
                    var editEleMany : EditText = view.findViewById(R.id.editEleMany)
                    editElename.setText(data[adapterPosition][0].toString())
                    editEleMany.setText(data[adapterPosition][1].toString())
                    editEleDan.setText(data[adapterPosition][2].toString())

                    val dialog:AlertDialog = builder.create()
                    btnEdit.setOnClickListener {
                        var strName:String = editElename.text.toString()
                        var strMany:String = editEleMany.text.toString()
                        var strDan : String = editEleDan.text.toString()


                        var dict : ArrayList<String> = arrayListOf(strName, strMany, strDan)
                        data.set(adapterPosition, dict)
                        notifyItemChanged(adapterPosition)
                        dialog.dismiss()
                    }
                    dialog.show()
                    true
                }
                1002 -> {

                    data.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, data.size)
                    true
                }

                else -> true
            }
        }


    }

}