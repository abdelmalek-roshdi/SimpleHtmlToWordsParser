package com.abdelmalek.simplehtmltowordsparser.data.datasource.datamanager

import android.util.Log
import com.abdelmalek.simplehtmltowordsparser.BuildConfig
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.WordsDataManager
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.networkutils.NetworkChecker
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.parser.HtmlParser
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.local.WordsLocalDataSource
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.remote.WordsRemoteDataSource
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import java.util.concurrent.Executor

class WordsDataManager(
    private val threadExecutor: Executor,
    private val remoteDataSource: WordsRemoteDataSource,
    private val localDataSource: WordsLocalDataSource,
    private val networkChecker: NetworkChecker,
    private val htmlParser: HtmlParser
) : WordsDataManager {

    private val networkFailureException by lazy {
        Exception("Error fetching data")
    }

    private val emptyDataException by lazy {
        Exception("Local database is empty")
    }

    override fun getWords(getWordsCallback: (WordsResponseResult) -> Unit) {
        threadExecutor.execute {
            if (networkChecker.isConnected()) {
                val wordsList = getRemoteData()
                when {
                    wordsList.isNullOrEmpty() -> getWordsCallback(
                        WordsResponseResult.Failure(
                            networkFailureException
                        )
                    )
                    else -> {
                        saveWords(wordsList)
                        getWordsCallback(WordsResponseResult.Success(wordsList))
                    }
                }
            } else {
                val wordsList = getLocalData()
                when {
                    wordsList.isEmpty() -> getWordsCallback(WordsResponseResult.Failure(emptyDataException))
                    else -> {
                        getWordsCallback(WordsResponseResult.Success(wordsList))
                    }
                }
            }

        }
    }

    override fun saveWords(words: Map<String, Int>): Boolean {
        return localDataSource.saveWords(words)
    }

    private fun getRemoteData(): Map<String, Int>? {
        return try {
            val parsedHtmlString = htmlParser.parseHtmlString(remoteDataSource.fetchWords())
            htmlParser.parseStringsToWords(parsedHtmlString)
        } catch (e: Exception) {
            if (BuildConfig.BUILD_TYPE == "debug") {
                Log.e(this::class.java.name, e.localizedMessage ?: "error fetching data")
            }
            null
        }

    }

    private fun getLocalData(): Map<String, Int> {
        return localDataSource.getWords()
    }
}
