/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.activity

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.grouph.recipelab.R
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Recipe
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test.btn_exit

class AddRecipeActivity : AppCompatActivity() {

    lateinit var btnExit: Button
    lateinit var editName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

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
