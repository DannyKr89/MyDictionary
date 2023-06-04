package ru.dk.mydictionary.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.dk.mydictionary.R
import ru.dk.mydictionary.databinding.ActivityMainBinding
import ru.dk.mydictionary.ui.history.HistoryFragment
import ru.dk.mydictionary.ui.list.SearchListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            openFragment(SearchListFragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                openFragment(HistoryFragment.newInstance())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(newInstance: Fragment) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.mainContainer.id, newInstance)
                .addToBackStack(newInstance.tag)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .add(binding.mainContainer.id, newInstance)
                .commit()
        }
    }
}