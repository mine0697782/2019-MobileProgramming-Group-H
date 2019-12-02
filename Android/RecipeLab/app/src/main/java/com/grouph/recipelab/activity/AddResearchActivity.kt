package com.grouph.recipelab.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.content.Context;
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.AddResearchAdapter
import com.grouph.recipelab.adapter.ResearchingListAdapter
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import kotlinx.android.synthetic.main.activity_add_research.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.collections.ArrayList

class AddResearchActivity : AppCompatActivity() {

    private lateinit var db : SQLiteDatabase
    private lateinit var helper : MySQLIteOpenHelper
    private lateinit var mEditRecipeNo : EditText
    private lateinit var btnDate : Button
    private lateinit var mEditScore : EditText
    private lateinit var cal : Calendar

    var td : ArrayList<String> = arrayListOf("원두", "10", "g")

    var data: ArrayList<ArrayList<String>> = arrayListOf()


    lateinit var rv: RecyclerView
    var mAdapter : AddResearchAdapter = AddResearchAdapter(this, data)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_research)

        helper = MySQLIteOpenHelper(this, "file.db", null, 1)

        mEditRecipeNo = findViewById(R.id.editRecipeNo)
        btnDate = findViewById(R.id.btnDate)
        mEditScore = findViewById(R.id.editScore)

        cal = Calendar.getInstance()
        rv = rv_add_element
        rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = true
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    public fun mOnClick(v: View) {

        when (v.getId()) {
            R.id.btnAdapt -> if (mEditRecipeNo.text.isNotEmpty()
                && btnDate.text.isNotEmpty() && mEditScore.text.isNotEmpty()) {
                try {
                    db = helper.writableDatabase
                    insertResearchTable(
                        mEditRecipeNo.text.toString().toInt(),
                        btnDate.text.toString().toIntOrNull(),
                        mEditScore.text.toString().toFloat()
                    )

                    for (element in data) {
                        insertElementsTable(1, element[0], element[1].toInt(), element[2])
                    }
                    helper.close()
                    finish()
                } catch (e: Exception) {}
            }
            R.id.btnDate -> DatePickerDialog(this@AddResearchActivity, mDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
            R.id.btnEle-> {
                print(data)
                var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                var view:View = LayoutInflater.from(this).inflate(R.layout.element_add_list, null, false)
                builder.setView(view)


                var btnEdit : Button = view.findViewById(R.id.btnEdit)
                var editElename: EditText = view.findViewById(R.id.editEleName)
                var editEleDan: EditText = view.findViewById(R.id.editEleDan)
                var editEleMany :EditText = view.findViewById(R.id.editEleMany)

                btnEdit.text = "삽입"
                var dialog : AlertDialog = builder.create()

                btnEdit.setOnClickListener(){
                    var EleName:String = editElename.text.toString()
                    var EleDan:String = editEleDan.text.toString()
                    var EleMany:String = editEleMany.text.toString()

                    var dict:ArrayList<String> = arrayListOf(EleName, EleMany, EleDan)
                    data.add(0,dict)
                    mAdapter.notifyDataSetChanged()

                    dialog.dismiss()

                }
                dialog.show()

                print(data)
            }
        }

    }
    public fun insertResearchTable(recipeNo:Int, date:Int?, score:Float ) {
        print("1")

        var sql : String = "insert into researchTable(recipeNo, date, score) values(?, ?, ?)"
        var params : Array<Any?> = arrayOf(recipeNo, date, score)
        db.execSQL(sql, params)
    }
    public fun insertElementsTable(researchNo:Int, name:String, value:Int, unit:String) {
        var sql : String = "insert into elementsTable(researchNo, name, value, unit) values(?, ?, ?, ?)"
        var params : Array<Any?> = arrayOf(researchNo, name, value, unit)
        db.execSQL(sql, params)
    }

    val mDateSetListener = DatePickerDialog.OnDateSetListener {
        view, year, month, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat)
        btnDate.text = sdf.format(cal.time)

    }

}

