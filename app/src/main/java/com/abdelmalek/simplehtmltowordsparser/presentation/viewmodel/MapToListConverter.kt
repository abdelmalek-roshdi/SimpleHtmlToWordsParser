package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word

class MapToListConverter : (Map<String, Int>) -> List<Word> {
    override fun invoke(map: Map<String, Int>): List<Word> {
        val list = mutableListOf<Word>()
        map.forEach {
            val word = Word(it.key, it.value)
            list.add(word)
        }
        return list
    }
}