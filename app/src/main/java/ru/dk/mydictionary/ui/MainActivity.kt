package ru.dk.mydictionary.ui

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import ru.dk.mydictionary.R
import ru.dk.mydictionary.databinding.ActivityMainBinding
import ru.dk.mydictionary.ui.history.HistoryFragment
import ru.dk.mydictionary.ui.list.SearchListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashAnimation()
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

    override fun onBackPressed() {
        if (supportFragmentManager.popBackStackImmediate())
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure?")
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setPositiveButton("Yes") { _, _ -> finish() }
            .show()
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

    private fun setupSplashAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().apply {
                setOnExitAnimationListener() { provider ->
                    ObjectAnimator.ofFloat(provider.iconView, View.ROTATION_Y, 0f, 360f).apply {
                        duration = 600
                        interpolator = AnticipateInterpolator()
                        doOnEnd {
                            provider.remove()
                        }
                    }.start()
                }
            }
        }
    }
}