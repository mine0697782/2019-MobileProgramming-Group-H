/**
 *  작성자 : 정민재
 */

package com.grouph.recipelab.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLIteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    // 데이터베이스가 존재하지 않으면 데이터베이스 생성을 위해 호출되는 함
    override fun onCreate(db: SQLiteDatabase?) {
        // recipeTable 생성
        var sql = "create table recipeTable(id integer primary key autoincrement, " +
                "eleNum integer, resNum integer, recipeName varchar(45), isFinished bit, " +
                "currentDate DATETIME, keyName1 varchar(45), keyName2 varchar(45), " +
                "keyName3 varchar(45))"
        db?.execSQL(sql)

        // researchTable 생성
        sql = "create table researchTable(researchNo integer primary key autoincrement, " +
                "recipeNo integer, date DATETIME, score float)"
        db?.execSQL(sql)

        // elementsTable 생성
        sql = "create table elementsTable(id integer primary key autoincrement, " +
                "researchNo integer, name varchar(45), value integer, unit varchar(45))"
        db?.execSQL(sql)
    }

    // 데이터베이스의 버전이 변경되면 호출되는 함수
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}