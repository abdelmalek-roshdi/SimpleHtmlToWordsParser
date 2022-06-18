package com.abdelmalek.simplehtmltowordsparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.abdelmalek.simplehtmltowordsparser.data.di.SimpleServiceLocator
import com.abdelmalek.simplehtmltowordsparser.databinding.ActivityMainBinding
import com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel.WordsViewModel
import com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel.WordsViewModelFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = ViewModelProvider(
                this@MainActivity,
                WordsViewModelFactory(SimpleServiceLocator.useCases)
            )[WordsViewModel::class.java]
        }.root)
    }
}