package com.sanitcode.footballclubapp.ui.activity.search.searchteam

import com.sanitcode.footballclubapp.model.team.TeamModel

interface SearchTeamView {
    fun showLoading(show: Boolean)
    fun showMessage(message: String)
    fun showTeamList(data: MutableList<TeamModel>)
}