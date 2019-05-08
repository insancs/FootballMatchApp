package com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam

import android.content.Context
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter(private val view: FavoriteTeamView, private val context: Context?) {

    fun getTeams() {
        context!!.database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
            val teams = result.parseList(classParser<FavoriteTeam>())
            view.showTeamList(teams)
            if (teams.isEmpty()) {
                view.showNoData(true)
            } else {
                view.showNoData(false)
            }
        }
    }
}