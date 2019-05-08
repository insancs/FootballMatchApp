package com.sanitcode.footballclubapp.ui.activity.detail.detailteam

import android.R.id.home
import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.drawable.ic_favorite
import com.sanitcode.footballclubapp.R.drawable.ic_favorite_border
import com.sanitcode.footballclubapp.R.id.add_to_favorite
import com.sanitcode.footballclubapp.R.menu.favorite
import com.sanitcode.footballclubapp.ui.base.BaseActivity
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.db.database
import com.sanitcode.footballclubapp.util.loadImage
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : BaseActivity() {

    private var idTeam: String = ""
    private val titles = arrayOf<CharSequence>("Overview", "Players")
    private val numberTabs = 2
    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        idTeam = intent.getStringExtra(FavoriteTeam.TEAM_ID)
        tv_team_name.text = intent.getStringExtra(FavoriteTeam.TEAM_NAME)
        tv_team_born.text = intent.getStringExtra(FavoriteTeam.FORMED_YEAR)
        tv_team_stadium.text = intent.getStringExtra(FavoriteTeam.STADIUM)
        localTeamState()
        val adapter = ViewPagerAdaper(supportFragmentManager, titles, numberTabs)
        viewpager_team.adapter = adapter
        tab_team_detail.setupWithViewPager(viewpager_team)

        if (!isFinishing) {
            if (!intent.getStringExtra(FavoriteTeam.TEAM_BADGE).isNullOrEmpty() && !intent.getStringExtra(FavoriteTeam.STADIUM_THUMB).isNullOrEmpty()) {
                loadImage(this, intent.getStringExtra(FavoriteTeam.TEAM_BADGE), iv_team_badge)
                loadImage(this, intent.getStringExtra(FavoriteTeam.STADIUM_THUMB), stadium_thumb)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite, menu)
        menuItem = menu
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            home -> {
                setResult(Activity.RESULT_OK)
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavourite) removeFromDB() else addToFavourite()
                isFavourite = !isFavourite
                setFavoriteIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun localTeamState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to idTeam)
            val favoriteTeam = result.parseList(classParser<FavoriteTeam>())
            if (!favoriteTeam.isEmpty()) {
                isFavourite = true
            }
        }
    }

    private fun removeFromDB() {
        try {
            database.use {
                //Menghapus TABLE_TEAM
                delete(FavoriteTeam.TABLE_TEAM, "(TEAM_ID = {id})",
                        "id" to idTeam)
            }
            //Muncul snackbar dengan text dari resource string/remove_team ketika team dihapus dari FavoriteTeam List
            detail_team.snackbar(getString(R.string.remove_team)).show()
        } catch (e: SQLiteConstraintException) {
            detail_team.snackbar(e.localizedMessage)
        }
    }
    private fun setFavoriteIcon() {
        if (isFavourite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_border)
    }

    private fun addToFavourite() {
        try {
            database.use {
                //Menambahkan TABLE_TEAM
                insert(FavoriteTeam.TABLE_TEAM,
                        FavoriteTeam.TEAM_ID to idTeam,
                        FavoriteTeam.TEAM_NAME to intent.getStringExtra(FavoriteTeam.TEAM_NAME),
                        FavoriteTeam.FORMED_YEAR to intent.getStringExtra(FavoriteTeam.FORMED_YEAR),
                        FavoriteTeam.STADIUM to intent.getStringExtra(FavoriteTeam.STADIUM),
                        FavoriteTeam.WEBSITE to intent.getStringExtra(FavoriteTeam.WEBSITE),
                        FavoriteTeam.DESC_EN to intent.getStringExtra(FavoriteTeam.DESC_EN),
                        FavoriteTeam.TEAM_BADGE to intent.getStringExtra(FavoriteTeam.TEAM_BADGE),
                        FavoriteTeam.TEAM_JERSEY to intent.getStringExtra(FavoriteTeam.TEAM_JERSEY),
                        FavoriteTeam.TEAM_FANART to intent.getStringExtra(FavoriteTeam.TEAM_FANART),
                        FavoriteTeam.MANAGER to intent.getStringExtra(FavoriteTeam.MANAGER),
                        FavoriteTeam.CAPACITY to intent.getStringExtra(FavoriteTeam.CAPACITY),
                        FavoriteTeam.LOCATION to intent.getStringExtra(FavoriteTeam.LOCATION),
                        FavoriteTeam.COUNTRY to intent.getStringExtra(FavoriteTeam.COUNTRY),
                        FavoriteTeam.STADIUM_DESC to intent.getStringExtra(FavoriteTeam.STADIUM_DESC),
                        FavoriteTeam.STADIUM_THUMB to intent.getStringExtra(FavoriteTeam.STADIUM_THUMB))
            }
            //Muncul snackbar dengan text dari resource string/added_team ketika team ditambahkan ke FavoriteTeam List
            detail_team.snackbar(getString(R.string.added_team)).show()
        } catch (e: SQLiteConstraintException) {
            detail_team.snackbar(e.localizedMessage)
        }
    }


}
