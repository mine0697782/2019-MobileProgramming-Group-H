/**
 *  작성자 : 최영준
 */

package com.grouph.recipelab.activity

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.RecipeListAdapter
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import kotlinx.android.synthetic.main.activity_researching_list.*
import kotlinx.android.synthetic.main.item_research_elements.*
import kotlinx.android.synthetic.main.toolbar_main.*

class ResearchingListActivity : AppCompatActivity() {

    private lateinit var helper: MySQLIteOpenHelper
    private lateinit var db: SQLiteDatabase

    lateinit var rvBottom: RecyclerView
    lateinit var adapterTop: RecipeListAdapter

    lateinit var textName: TextView
    lateinit var textDate: TextView
    lateinit var textResNum: TextView

    lateinit var keyName1: TextView
    lateinit var keyName2: TextView
    lateinit var keyName3: TextView

    var recipeNo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_researching_list)
        val myToolbar: Toolbar = toolbar

        myToolbar.title = "ResearchingListActicity"
        setSupportActionBar(myToolbar)

        textName = text_recipe_name
        textDate = text_date
        textResNum = text_resNum

        keyName1 = text_keyname1
        keyName2 = text_keyname2
        keyName3 = text_keyname3

        val intent = intent
        recipeNo = intent.getIntExtra("recipeNo", 0)

        textName.text = intent.getStringExtra("recipeName")
        textResNum.text = intent.getIntExtra("resNum", 0).toString()+"개"
        textDate.text = intent.getStringExtra("date")
        keyName1.text = intent.getStringExtra("keyName1")
        keyName2.text = intent.getStringExtra("keyName2")
        keyName3.text = intent.getStringExtra("keyName3")

        helper = MySQLIteOpenHelper(this, "file.db",null, 1)

        /*try {
            db = helper.readableDatabase
            var cursor = db.rawQuery("select * from recipeTable where isFInished = 0", null)
            while (cursor.moveToNext()) {
                cursor.apply {
                    val data = Recipe(getString(3), getInt(1),
                        getString(6), getString(7), getString(8),
                        getInt(2))
                    dataTop.add(data)
                }
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }*/

    }
}
