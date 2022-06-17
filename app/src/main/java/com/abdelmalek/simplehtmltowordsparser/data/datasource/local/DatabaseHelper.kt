package com.abdelmalek.simplehtmltowordsparser.data.datasource.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "WordsDatabase.db", null, 1) {

    private val wordsTable by lazy { "words" }
    private val wordColumn by lazy { "word" }
    private val countColumn by lazy { "count" }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table words " +
                    "(id integer primary key autoincrement, word text not null, count integer)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS WordsDatabase.db")
        onCreate(db)
    }

    fun getWordsCount(): Long {
        val sqlStatement = "SELECT COUNT(*) FROM $wordsTable"
        val db = this.readableDatabase
        val statement: SQLiteStatement = db.compileStatement(sqlStatement)
        return statement.simpleQueryForLong()
    }

    fun insertWord(
        word: String,
        count: Int,
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(wordColumn, word)
        contentValues.put(countColumn, count)
        db.insert(wordsTable, null, contentValues)
        return true
    }

    fun deleteAllWords(): Boolean {
        val db = this.writableDatabase
        db.delete(wordsTable, null, null)
        return true
    }

    fun getWords(): Map<String, Int> {
        val words = LinkedHashMap<String, Int>()
        val db = this.readableDatabase
        val sqlStatement = "select * from $wordsTable"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val word = cursor.getString(1)
            words[word] = cursor.getInt(2)
            cursor.moveToNext()
        }
        cursor.close()
        return words
    }
}