package com.sanitcode.footballclubapp.ui.activity.detail.detailmatch

import com.sanitcode.footballclubapp.model.match.MatchModel

interface DetailMatchView {
    fun showLoading(show: Boolean)
    fun showBadgeImageHome(string: String)
    fun showBadgeImageAway(string: String)
    fun showMessage(message: String)
    fun showDetailMatch(data: MatchModel)
    fun showError(show: Boolean)
}