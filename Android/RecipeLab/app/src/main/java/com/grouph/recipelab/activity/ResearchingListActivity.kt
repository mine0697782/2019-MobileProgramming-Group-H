/**
 *  작성자 : 최영준
 */

package com.grouph.recipelab.activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.RecipeListAdapter
import com.grouph.recipelab.adapter.ResearchListAdapter
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Research
import kotlinx.android.synthetic.main.activity_researching_list.*
import kotlinx.android.synthetic.main.activity_researching_list.fab
import kotlinx.android.synthetic.main.item_research_elements.*
import kotlinx.android.synthetic.main.toolbar_main.*

class ResearchingListActivity : AppCompatActivity() {

    private val TAG = "ResearchingListActivity"

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
    var eleNum: Int = 0

    lateinit var rv: RecyclerView
    lateinit var adapter: ResearchListAdapter

    var recipeNo: Int = 0

    var myData = arrayListOf<Research>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_researching_list)
        val myToolbar: Toolbar = toolbar

        myToolbar.title = "연구 목록"
        setSupportActionBar(myToolbar)

        val window = window
        window.statusBarColor = resources.getColor(R.color.colorPrimary)

        val floatingBtn: FloatingActionButton = fab
        floatingBtn.setOnClickListener {
            //            adapterTop.data.add(Recipe("추가된 데이터",0, "1","2","3"))
//            adapterTop.notifyDataSetChanged()
            startActivityForResult(Intent(this, AddResearchActivity::class.java), 0)
        }

        Log.d(TAG, "시작")

        textName = text_recipe_name
        textDate = text_date
        textResNum = text_resNum

        keyName1 = text_keyname1
        keyName2 = text_keyname2
        keyName3 = text_keyname3

        val intent = intent
        recipeNo = intent.getIntExtra("recipeNo", 0)
        Log.d(TAG, "recipeNo = "+recipeNo)

        eleNum = intent.getIntExtra("eleNum", 0)
        textName.text = intent.getStringExtra("recipeName")
        textResNum.text = intent.getIntExtra("resNum", 0).toString()+"개"
        textDate.text = intent.getStringExtra("date")
        keyName1.text = intent.getStringExtra("keyName1")
        keyName2.text = intent.getStringExtra("keyName2")
        keyName3.text = intent.getStringExtra("keyName3")

        helper = MySQLIteOpenHelper(this, "file.db",null, 1)

//        Log.d(TAG, "곧 try")
        try {
//            Log.d(TAG, "try 시작")
            db = helper.readableDatabase
            var resCursor = db.rawQuery("select * from researchTable where recipeNo = "+recipeNo, null)
            while (resCursor.moveToNext()) {
//                Log.d(TAG, "while 진입")
                resCursor.apply {
                    val data = Research(recipeNo, getFloat(3), getString(2).slice(IntRange(5, 9)), getInt(0))
                    myData.add(data)
                }
            }
            db.close()
//            Log.d(TAG, "try 끝")
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
//        Log.d(TAG, "try 탈출")

        rv = rv_list_researches
        adapter = ResearchListAdapter(myData, recipeNo, eleNum,this, helper)
        adapter.notifyDataSetChanged()
        rv.adapter = adapter
        rv.apply {
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context , DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = false
        }
    }
}
