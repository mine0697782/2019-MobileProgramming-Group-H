/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import com.grouph.recipelab.model.Element
import com.grouph.recipelab.model.Recipe
import com.grouph.recipelab.model.Research
import java.text.SimpleDateFormat
import java.util.*

class MySQLIteOpenHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {

    // 테스트용 데이터
    val d1 = arrayListOf(
        Recipe("에티오피아 예거치프", 3, "원두", "물", "시간", resNum = 3),
        Recipe("케냐 AA", 3, "원두", "물", "시간"),
        Recipe("브라질 산토스", 3, "원두", "물", "시간", isFinished = true),
        Recipe("인도네시아 블루문", 3, "원두", "물", "시간", isFinished = true))
    val d2 = arrayListOf(Research(1), Research(1), Research(1))
    val d3 = arrayListOf(
        Element(1, -1, 1, "원두", unit = "g"),
        Element(1, -1, 2, "물", unit = "ml"),
        Element(1, -1, 3, "시간", unit = "sec"),
        Element(1, 1, 1, "원두", 10, "g"),
        Element(1, 1, 2, "물", 10, "ml"),
        Element(1, 1, 3, "시간", 10, "sec"),
        Element(1, 2, 1, "원두", 10, "g"),
        Element(1, 2, 2, "물", 10, "ml"),
        Element(1, 2, 3, "시간", 10, "sec"),
        Element(1, 3, 1, "원두", 10, "g"),
        Element(1, 3, 2, "물", 10, "ml"),
        Element(1, 3, 3, "시간", 10, "sec"),
        Element(2, -1, 1, "원두", unit = "g"),
        Element(2, -1, 2, "물", unit = "ml"),
        Element(2, -1, 3, "시간", unit = "sec"),
        Element(3, -1, 1, "원두", unit = "g"),
        Element(3, -1, 2, "물", unit = "ml"),
        Element(3, -1, 3, "시간", unit = "sec"),
        Element(4, -1, 1, "원두", unit = "g"),
        Element(4, -1, 2, "물", unit = "ml"),
        Element(4, -1, 3, "시간", unit = "sec")
    )

    /** 데이터베이스가 존재하지 않으면 데이터베이스 생성을 위해 호출되는 함수 */
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("MySQLiteOpenHelper", "onCreate")

        /** recipeTable 생성 */
        var sql = "create table recipeTable(recipeNo integer primary key autoincrement, " +
                "eleNum integer, resNum integer, recipeName varchar(45), isFinished bit, " +
                "currentDate varchar(45), keyName1 varchar(45), keyName2 varchar(45), " +
                "keyName3 varchar(45))"
        db.execSQL(sql)

        /** researchTable 생성 */
        sql = "create table researchTable(researchNo integer primary key autoincrement, " +
                "recipeNo integer, date varchar(45), score float)"
        db.execSQL(sql)

        /** elementsTable 생성 */
        sql = "create table elementsTable(id integer primary key autoincrement, " +
                "recipeNo integer, researchNo integer, eleNo integer, name varchar(45), " +
                "value integer, unit varchar(45))"
        db.execSQL(sql)

        for (recipe in d1) {
            insert(db, recipe)
        }
        for (research in d2) {
            insert(db, research)
        }
        for (element in d3) {
            insert(db, element)
        }
    }

    fun insert(db: SQLiteDatabase, recipe: Recipe) {
        recipe.apply {
            //            val db = writableDatabase
            var fin = 0; if (isFinished) {
            fin = 1
        }
            val sql = "insert into recipeTable (eleNum, resNum, recipeName, " +
                    "isFinished, currentDate, keyName1, keyName2, keyName3) values (" +
                    eleNum + ", " + resNum + ", '" + recipeName + "', " + fin + ", '" +
                    currentDate + "', '" + keyName1 + "', '" + keyName2 + "', '" + keyName3 + "')"
            db.execSQL(sql)
        }
    }

    fun insert(db: SQLiteDatabase, research: Research) {
        research.apply {
            //            val db = writableDatabase
            val sql = "insert into researchTable (recipeNo, date, score) values(" +
                    recipeNo + ", '" + date + "', " + score + ")"
            db.execSQL(sql)
        }
    }

    fun insert(db: SQLiteDatabase, element: Element) {
        element.apply {
            //            val db = writableDatabase
            val sql = "insert into elementsTable (recipeNo, researchNo, " +
                    "eleNo, name, value, unit) values (" + recipeNo + ", " + researchNo +
                    ", " + eleNo + ", '" + name + "', " + value + ", '" + unit + "')"
            db.execSQL(sql)
        }
    }

    /** 데이터베이스의 버전이 변경되면 호출되는 함수 */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}