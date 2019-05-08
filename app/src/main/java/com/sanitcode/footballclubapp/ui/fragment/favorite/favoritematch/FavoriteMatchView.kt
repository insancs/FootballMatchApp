package com.sanitcode.footballclubapp.ui.fragment.favorite.favoritematch

import com.sanitcode.footballclubapp.db.FavoriteMatch

interface FavoriteMatchView {
    fun showMatchList(data: List<FavoriteMatch>)
    fun showNoData(show: Boolean)
}