package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import org.junit.Assert.assertEquals
import org.junit.Test

class MapToListConverterTest {

    @Test
    fun invoke_shouldReturnList() {
        val wordsMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3
        )

        val expectedValue = listOf(
            Word("one", 1),
            Word("two", 2),
            Word("three", 3)
        )

        assertEquals(expectedValue, MapToListConverter()(wordsMap))
    }
}
