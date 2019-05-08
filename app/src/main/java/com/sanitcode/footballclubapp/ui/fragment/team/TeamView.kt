package com.sanitcode.footballclubapp.ui.fragment.team

import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.model.team.TeamModel

interface TeamView {
    fun showLoading(show: Boolean)
    fun showMessage(message: String)
    fun showTeamList(data: MutableList<TeamModel>)
    fun showLeagueList(data: List<League>)
    fun showSpinner(show: Boolean)
}