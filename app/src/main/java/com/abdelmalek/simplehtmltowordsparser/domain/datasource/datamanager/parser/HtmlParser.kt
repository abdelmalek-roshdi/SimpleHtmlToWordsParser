package com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.parser

interface HtmlParser {
    fun parseHtmlString(htmlString: String): String
    fun parseStringsToWords(words: String): Map<String, Int>
}