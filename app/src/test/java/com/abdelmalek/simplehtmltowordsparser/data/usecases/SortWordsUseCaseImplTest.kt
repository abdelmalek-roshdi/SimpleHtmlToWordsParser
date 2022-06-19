package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.sorting.WordsSorting
import org.junit.Assert.*

import org.junit.Test

class SortWordsUseCaseImplTest {

    @Test
    fun sortWords_ascending_shouldReturnSortedListAscending() {
        val list = listOf(
            Word("a", 3),
            Word("b", 2),
            Word("c", 1)
        )
        val expectedValueList = listOf(
            Word("c", 1),
            Word("b", 2),
            Word("a", 3)

        )
        val useCase = SortWordsUseCaseImpl()

        assertEquals(expectedValueList, useCase.sortWords(list, WordsSorting.ASC))
    }

    @Test
    fun sortWords_descending_shouldReturnSortedListDescending() {
        val list = listOf(
            Word("a", 3),
            Word("b", 2),
            Word("c", 1)
        )
        val expectedValueList = listOf(
            Word("a", 3),
            Word("b", 2),
            Word("c", 1),

        )
        val useCase = SortWordsUseCaseImpl()

        assertEquals(expectedValueList, useCase.sortWords(list, WordsSorting.DESC))
    }
}
