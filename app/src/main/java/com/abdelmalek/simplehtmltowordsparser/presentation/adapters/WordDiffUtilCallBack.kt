package com.abdelmalek.simplehtmltowordsparser.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word

class WordDiffUtilCallBack : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word
    }
}