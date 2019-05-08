package com.sanitcode.footballclubapp.ui.fragment.match.lastmatch

import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.db.League

interface MatchView {
    fun showLoading(show: Boolean)
    fun showMessage(message: String)
    fun showMatchList(data: MutableList<ItemModel>)
    fun showLeagueList(data: List<League>)
    fun showSpinner(show: Boolean)
}