package com.abdelmalek.simplehtmltowordsparser.data.datasource.local

import android.content.Context
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.local.WordsLocalDataSource

class WordsLocalDataSourceImpl(context: Context) : WordsLocalDataSource {

    private val dbHelper by lazy { DatabaseHelper(context) }

    override fun saveWords(words: Map<String, Int>): Boolean {
        dbHelper.deleteAllWords()

        words.forEach {
            dbHelper.insertWord(word = it.key, count = it.value)
        }

        dbHelper.close()
        return true
    }

    override fun getWords(): Map<String, Int> {
        val words = dbHelper.getWords()
        dbHelper.close()
        return words
    }
}