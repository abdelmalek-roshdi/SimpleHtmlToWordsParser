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

class WordsDataManagerImpl(
    private val threadExecutor: Executor,
    private val remoteDataSource: WordsRemoteDataSource,
    private val localDataSource: WordsLocalDataSource,
    private val networkChecker: NetworkChecker,
    private val htmlParser: HtmlParser
) : WordsDataManager {

    private val networkFailureException by lazy {
        Exception("Error fetching data")
    }

    override fun getWords(getWordsCallback: (WordsResponseResult) -> Unit) {
        threadExecutor.execute {
            try {
                if (networkChecker.isConnected()) {
                    when (val wordsList = getRemoteData()) {
                        null -> getWordsCallback(
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
                    getWordsCallback(WordsResponseResult.Success(getLocalData()))
                }
            } catch (e: Exception) {
                getWordsCallback(WordsResponseResult.Failure(e))
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
