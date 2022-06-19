package com.abdelmalek.simplehtmltowordsparser.data.datasource.datamanager.parser

import android.os.Build
import android.text.Html
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.parser.HtmlParser

class HtmlParserImpl : HtmlParser {

    private val styleRegex by lazy {
        "<style([\\s\\S]+?)</style>".toRegex()
    }

    private val scriptRegex by lazy {
        "<script([\\s\\S]+?)</script>".toRegex()
    }

    override fun parseHtmlString(htmlString: String): String {

        var words = htmlString.replace(styleRegex, " ")

        words = words.replace(scriptRegex, " ")

        words = words.replace("</span>", " </span>")

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(words, Html.FROM_HTML_MODE_LEGACY, null, HtmlTagHandler()).toString()
        } else {
            Html.fromHtml(words).toString()
        }

    }

    override fun parseStringsToWords(words: String): Map<String, Int> {
        var parsedWords = words.replace("\n", " ")
        parsedWords = parsedWords.replace("( +)".toRegex(), " ")
        val wordsArray = parsedWords.split(" ")
        val wordsMap = HashMap<String, Int>()
        wordsArray.forEach {
            val word = it.replace("[^A-Za-z0-9]".toRegex(), "")
            if (word != "") {
                if (wordsMap.containsKey(word)) {
                    wordsMap[word] = wordsMap[word]!! + 1
                } else {
                    wordsMap[word] = 1
                }
            }
        }
        return wordsMap
    }
}