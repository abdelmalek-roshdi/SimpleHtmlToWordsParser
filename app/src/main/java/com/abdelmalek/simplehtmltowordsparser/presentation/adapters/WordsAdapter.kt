package com.abdelmalek.simplehtmltowordsparser.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdelmalek.simplehtmltowordsparser.databinding.WordListItemBinding
import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word

class WordsAdapter : ListAdapter<Word, WordsAdapter.WordsViewHolder>(WordDiffUtilCallBack()) {
    companion object {
        @JvmStatic
        @BindingAdapter("bindWordsList")
        fun bindWords(recyclerView: RecyclerView, words: List<Word>?) {
            if (recyclerView.adapter == null) {
                val adapter = WordsAdapter()
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
                adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeMoved(
                        fromPosition: Int,
                        toPosition: Int,
                        itemCount: Int
                    ) {
                        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
                    }
                })
            }
            words?.let {
                (recyclerView.adapter as WordsAdapter).submitList(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            WordListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bindWord(getItem(position))
    }

    inner class WordsViewHolder(private val bindingImpl: WordListItemBinding) :
        RecyclerView.ViewHolder(bindingImpl.root) {
        fun bindWord(word: Word) {
            bindingImpl.wordModel = word
            bindingImpl.executePendingBindings()
        }
    }
}