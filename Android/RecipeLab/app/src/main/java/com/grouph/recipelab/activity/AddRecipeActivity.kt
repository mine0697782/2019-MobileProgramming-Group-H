/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.activity

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.AddElementListAdapter
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Recipe
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_test.btn_exit

class AddRecipeActivity : AppCompatActivity() {

    private val TAG = "AddRecipeActivity"

    lateinit var btnAdd: TextView

    lateinit var btnExit: Button
    lateinit var editName: EditText
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AddElementListAdapter

    private val data = arrayListOf<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        rv = rv_list_element_to_add
        adapter = AddElementListAdapter(data, this)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        btnAdd = btn_add_element
        btnAdd.setOnClickListener{
            Log.d(TAG, "btnAdd")
            adapter.data.add(arrayListOf<String>())
            adapter.notifyDataSetChanged()
        }

        editName = edit_recipe_name
        btnExit = btn_exit
        btnExit.setOnClickListener {
            val helper = MySQLIteOpenHelper(this, "file.db",null, 1)

            try {
                val db = helper.writableDatabase
                helper.insert(db, Recipe(editName.text.toString()))
                db.close()
            } catch (e: SQLiteException) {
                e.printStackTrace()
            }

            val intent = Intent(this,MainActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }

        //Recipe("추가된 데이터",0, "1","2","3")
    }
}
