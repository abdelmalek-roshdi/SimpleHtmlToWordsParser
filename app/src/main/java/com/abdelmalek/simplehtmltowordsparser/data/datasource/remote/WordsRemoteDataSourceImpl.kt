package com.abdelmalek.simplehtmltowordsparser.data.datasource.remote

import com.abdelmalek.simplehtmltowordsparser.domain.datasource.remote.WordsRemoteDataSource
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class WordsRemoteDataSourceImpl : WordsRemoteDataSource {
    var content = ""
    override fun fetchWords(): String {
        val url = URL("https://instabug.com/")

        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
            content = readStream(inputStream)
        } finally {
            urlConnection.disconnect()
        }
        return content
    }

    private fun readStream(inputStream: InputStream): String {
        val reader = BufferedReader(inputStream.reader())
        return try {
            val content = reader.readText()
            reader.close()
            content
        } catch (e: Exception) {
            reader.close()
            e.message.toString()
        }
    }
}