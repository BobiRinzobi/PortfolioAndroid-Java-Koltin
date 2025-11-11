package com.mylibrary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context : Context, val factory : SQLiteDatabase.CursorFactory? ) :
    SQLiteOpenHelper(context, "Users", factory, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val command = "CREATE TABLE users (id INT PRIMARY KEY, email TEXT, password TEXT)"
        db!!.execSQL(command)
    }

    override fun onUpgrade( db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser( user : User)
    {
        val values = ContentValues()
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase
        db!!.insert("Users",null, values)

        db.close()

    }

    fun getUser(email : String, password : String) : Boolean
    {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM Users WHERE email = '$email' AND password = '$password'",null)

        return result.moveToFirst()
    }

}