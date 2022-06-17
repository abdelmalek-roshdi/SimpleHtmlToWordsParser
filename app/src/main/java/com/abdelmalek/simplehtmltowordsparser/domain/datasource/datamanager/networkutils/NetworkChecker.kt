package com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.networkutils

interface NetworkChecker {
    fun isConnected(): Boolean
}