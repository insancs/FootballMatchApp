package com.sanitcode.footballclubapp.ui.fragment.favorite.favoritematch

import android.content.Context
import com.sanitcode.footballclubapp.db.FavoriteMatch
import com.sanitcode.footballclubapp.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter (private val view: FavoriteMatchView, private val context: Context?) {

    fun getMatch() {
        context!!.database.use {
            val result = select(FavoriteMatch.TABLE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            view.showMatchList(favorite)
            if (favorite.isEmpty()) {
                view.showNoData(true)
            } else {
                view.showNoData(false)
            }
        }
    }
}