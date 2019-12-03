/**
 *  작성자 : 김경준
 */


package com.grouph.recipelab.activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.AddResearchAdapter2
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Element
import com.grouph.recipelab.model.Research
import kotlinx.android.synthetic.main.activity_add_research2.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.lang.Exception

class AddResearchActivity2 : AppCompatActivity() {

    var recipeNo: Int = 0
    lateinit var textName: TextView
    lateinit var textDate: TextView
    lateinit var textNum: TextView
    lateinit var btnRegister: Button

    lateinit var helper: MySQLIteOpenHelper
    lateinit var db: SQLiteDatabase

    lateinit var rv: RecyclerView
    lateinit var adapter: AddResearchAdapter2
    lateinit var editScore: EditText

    val data = arrayListOf<Element>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_research2)

        val myToolbar: Toolbar = toolbar
        myToolbar.title = "연구 추가"
        setSupportActionBar(myToolbar)

        val window = window
        window.statusBarColor = resources.getColor(R.color.colorPrimary)

        btnRegister = btn_register
        btnRegister.setOnClickListener {
            helper.insert(db, Research(recipeNo, editScore.text.toString().toFloat()))
            val cursor = db.rawQuery("select count(*) from researchTable", null)
            cursor.moveToFirst()
            val researchNo = cursor.getInt(0)
            for (i in 0 until adapter.itemCount) {
                helper.insert(db, Element(recipeNo, researchNo, i+1, adapter.data[i].name,
                    adapter.mDataset[i].toInt(), adapter.data[i].unit))
            }
            val intent = Intent(this, ResearchingListActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }

        textName = text_recipe_name
        textDate = text_date
        textNum = text_resNum
        editScore = edit_score

        val intent = intent
        recipeNo = intent.getIntExtra("recipeNo", 0)
        textName.text = intent.getStringExtra("recipeName")
        textDate.text = intent.getStringExtra("date")
        textNum.text = intent.getIntExtra("resNum", 0).toString()+"개"

        adapter = AddResearchAdapter2(data)

        helper = MySQLIteOpenHelper(this, "file.db",null, 1)
        try {
            db = helper.writableDatabase
            val cursor = db.rawQuery("select * from elementsTable where researchNo=-1 and recipeNo="+recipeNo, null)
            while (cursor.moveToNext()) {
                data.add(Element(recipeNo, name = cursor.getString(4), unit = cursor.getString(6)))
                adapter.mDataset.add("")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        rv = rv_list_element_add_research
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter

    }
}
