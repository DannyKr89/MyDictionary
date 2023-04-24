package ru.dk.mydictionary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dk.mydictionary.databinding.ActivityMainBinding
import ru.dk.mydictionary.ui.list.SearchListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.mainContainer.id, SearchListFragment.newInstance())
                .commit()
        }
    }
}