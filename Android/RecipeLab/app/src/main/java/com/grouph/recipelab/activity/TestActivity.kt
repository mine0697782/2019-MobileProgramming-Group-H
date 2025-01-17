/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.grouph.recipelab.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        btnExit = btn_register
        btnExit.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
