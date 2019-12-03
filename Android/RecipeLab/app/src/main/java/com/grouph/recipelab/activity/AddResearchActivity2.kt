/**
 *  작성자 : 김경준
 */


package com.grouph.recipelab.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.grouph.recipelab.R
import kotlinx.android.synthetic.main.activity_add_research2.*
import kotlinx.android.synthetic.main.toolbar_main.*

class AddResearchActivity2 : AppCompatActivity() {

    var recipeNo: Int = 0
    lateinit var textName: TextView
    lateinit var textDate: TextView
    lateinit var textNum: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_research2)

        val myToolbar: Toolbar = toolbar
        myToolbar.title = "연구 추가"
        setSupportActionBar(myToolbar)

        val window = window
        window.statusBarColor = resources.getColor(R.color.colorPrimary)

        textName = text_recipe_name
        textDate = text_date
        textNum = text_resNum

        val intent = intent
        recipeNo = intent.getIntExtra("recipeNo", 0)
        textName.text = intent.getStringExtra("recipeName")
        textDate.text = intent.getStringExtra("date")
        textNum.text = intent.getIntExtra("resNum", 0).toString()+"개"

    }
}
