package com.abdelmalek.simplehtmltowordsparser.data.di

import android.content.Context
import com.abdelmalek.simplehtmltowordsparser.data.datasource.datamanager.WordsDataManagerImpl
import com.abdelmalek.simplehtmltowordsparser.data.datasource.datamanager.parser.HtmlParserImpl
import com.abdelmalek.simplehtmltowordsparser.data.datasource.local.WordsLocalDataSourceImpl
import com.abdelmalek.simplehtmltowordsparser.data.datasource.remote.WordsRemoteDataSourceImpl
import com.abdelmalek.simplehtmltowordsparser.data.networkutils.NetworkCheckerImpl
import com.abdelmalek.simplehtmltowordsparser.data.usecases.GetWordsUseCaseImpl
import com.abdelmalek.simplehtmltowordsparser.data.usecases.SearchWordsUseCaseImpl
import com.abdelmalek.simplehtmltowordsparser.data.usecases.SortWordsUseCaseImpl
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.models.UseCasesModel
import java.util.concurrent.Executors

object SimpleServiceLocator {

    private const val THREAD_POOL_COUNT = 4
    private var dataManager: WordsDataManagerImpl? = null

    fun init(context: Context) {
        dataManager = WordsDataManagerImpl(
            Executors.newScheduledThreadPool(THREAD_POOL_COUNT),
            WordsRemoteDataSourceImpl(),
            WordsLocalDataSourceImpl(context),
            NetworkCheckerImpl(context),
            HtmlParserImpl()
        )
    }

    val useCases by lazy {
        UseCasesModel(
            GetWordsUseCaseImpl(requireNotNull(dataManager)),
            SearchWordsUseCaseImpl(),
            SortWordsUseCaseImpl()
        )
    }
}