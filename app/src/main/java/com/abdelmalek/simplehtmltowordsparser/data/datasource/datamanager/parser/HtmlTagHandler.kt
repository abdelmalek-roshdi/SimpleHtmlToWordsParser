package com.abdelmalek.simplehtmltowordsparser.data.datasource.datamanager.parser

import android.text.Editable
import android.text.Html
import org.xml.sax.XMLReader

class HtmlTagHandler : Html.TagHandler {

    override fun handleTag(p0: Boolean, p1: String?, p2: Editable?, p3: XMLReader?) {
        p2?.append(" ")
    }

}