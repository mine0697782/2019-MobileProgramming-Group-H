/**
 * 작성자 최민혁
 */


package com.grouph.recipelab.activity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.grouph.recipelab.R
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import kotlinx.android.synthetic.main.activity_research_result_activity.*

class RecipeFin(val name: String, val date: String) {}

class ResearchResultActivity : AppCompatActivity() {

    private lateinit var helper: MySQLIteOpenHelper
    private lateinit var db: SQLiteDatabase

    val datalist = arrayListOf<RecipeFin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research_result_activity)

        helper = MySQLIteOpenHelper(this, "file.db", null, 1)
        db = helper.readableDatabase
        try {
            val cursor = db.rawQuery("select * from recipeTable where isFinished = 1", null)
            while (cursor.moveToNext()) {
                val name = cursor.getString(3)
                val date = cursor.getString(5)
                datalist.add(RecipeFin(name, date))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val coffeeAdapter = Adapter(this, datalist)
        list_research_result.adapter = coffeeAdapter
    }

    inner class Adapter (val context: Context, val data: ArrayList<RecipeFin>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_research_result_list, null)

            val coffeeName = view.findViewById<TextView>(R.id.coffee_name)
            val coffeeDate = view.findViewById<TextView>(R.id.coffee_date)

            val coffee = data[position]
            coffeeName.text = coffee.name
            coffeeDate.text = coffee.date

            return view
        }
        override fun getItem(position: Int): Any {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return data.size
        }
    }
}
