package com.abdelmalek.simplehtmltowordsparser.domain.datasource.remote

interface WordsRemoteDataSource {
    fun fetchWords(): String
}