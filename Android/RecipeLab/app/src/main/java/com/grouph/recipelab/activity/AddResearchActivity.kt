package com.grouph.recipelab.activity

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.content.Context;
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.grouph.recipelab.R
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import kotlinx.android.synthetic.main.activity_add_research.*
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class AddResearchActivity : AppCompatActivity() {

    private lateinit var db : SQLiteDatabase
    private lateinit var helper : MySQLIteOpenHelper
    private lateinit var mEditRecipeNo : EditText
    private lateinit var btnDate : Button
    private lateinit var mEditScore : EditText
    private lateinit var cal : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_research)

        helper = MySQLIteOpenHelper(this, "file.db", null, 1)
        mEditRecipeNo = findViewById(R.id.editRecipeNo)
        btnDate = findViewById(R.id.btnDate)
        mEditScore = findViewById(R.id.editScore)

        cal = Calendar.getInstance()
    }
    var a :String = "1"



    @RequiresApi(Build.VERSION_CODES.O)
    public fun mOnClick(v: View) {

        when (v.getId()) {
            R.id.btnAdapt -> if (mEditRecipeNo.text.isNotEmpty()
                && btnDate.text.isNotEmpty() && mEditScore.text.isNotEmpty()) {

                db = helper.writableDatabase
                insertData(mEditRecipeNo.text.toString().toInt(), btnDate.text.toString().toIntOrNull(), mEditScore.text.toString().toFloat())
                helper.close()
            }
            R.id.btnDate -> DatePickerDialog(this@AddResearchActivity, mDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }
    public fun insertData(recipeNo:Int, date:Int?, score:Float ) {
        print("1")

        var sql : String = "insert into researchTable(recipeNo, date, score) values(?, ?, ?)"
        var params : Array<Any?> = arrayOf(recipeNo, date, score)
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

