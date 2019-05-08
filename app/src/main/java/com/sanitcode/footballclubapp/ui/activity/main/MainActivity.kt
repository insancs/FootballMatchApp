package com.sanitcode.footballclubapp.ui.activity.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.ui.activity.search.searchmatch.SearchMatchActivity
import com.sanitcode.footballclubapp.ui.activity.search.searchteam.SearchTeamActivity
import com.sanitcode.footballclubapp.ui.fragment.tab.tabfavorite.FavoriteTabFragment
import com.sanitcode.footballclubapp.ui.fragment.tab.tabmatch.MatchTabFragment
import com.sanitcode.footballclubapp.ui.fragment.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Action ketika Navigation Botton di klik
        nav_bottom.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
            //Menampilkan MatchFragment
                nav_match -> setupMatch(savedInstanceState)
            //Menampilkan TeamModel Fragment
                nav_team -> setupTeams(savedInstanceState)
            //Menampilkan Favorite Fragment
                nav_favorite -> setupFavorite(savedInstanceState)
            }
            true
        }
        //setup pertama kali aplikasi dijalankan
        nav_bottom.selectedItemId = nav_match
    }

    override fun onBackPressed() {
        //Action ketika tombok back ditekan di Main Activity
        AlertDialog.Builder(this)
                .setMessage(resources.getString(R.string.alerttext))
                .setCancelable(false)
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ -> this@MainActivity.finish() }
                .setNegativeButton(resources.getString(R.string.no), null)
                .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuInflater.inflate(R.menu.menu_language, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
        //Action ketika item menu dengan id = search_match ditekan
            R.id.search_match -> {
                startActivity<SearchMatchActivity>()
            }
        //Action ketika item menu dengan id = search_team ditekan
            R.id.search_team -> {
                startActivity<SearchTeamActivity>()
            }
        //Action ketika item menu dengan id = action_language ditekan
            R.id.action_language -> {
                val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MatchTabFragment(), MatchTabFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun setupTeams(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, TeamFragment(), TeamFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun setupFavorite(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FavoriteTabFragment(), FavoriteTabFragment::class.java.simpleName)
                    .commit()
        }
    }
}
