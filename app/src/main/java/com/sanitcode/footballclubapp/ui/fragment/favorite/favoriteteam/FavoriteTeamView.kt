package com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam

import com.sanitcode.footballclubapp.db.FavoriteTeam

interface FavoriteTeamView {
    fun showTeamList(data: List<FavoriteTeam>)
    fun showNoData(show: Boolean)
}