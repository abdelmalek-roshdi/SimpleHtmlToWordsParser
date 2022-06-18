package com.abdelmalek.simplehtmltowordsparser.presentation.app

import android.app.Application
import com.abdelmalek.simplehtmltowordsparser.data.di.SimpleServiceLocator

class HtmlToWordsParserApp: Application() {
    override fun onCreate() {
        super.onCreate()
        SimpleServiceLocator.init(this.applicationContext)
    }
}