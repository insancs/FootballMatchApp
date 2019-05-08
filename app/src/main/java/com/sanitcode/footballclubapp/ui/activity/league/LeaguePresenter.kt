package com.sanitcode.footballclubapp.ui.activity.league

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.sanitcode.footballclubapp.model.league.LeagueResponse
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.db.database
import com.sanitcode.footballclubapp.model.league.LeagueModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class LeaguePresenter(private val view: LeagueView, private val service: ApiSportDB,
                      private val context: Context) {

    private val composite = CompositeDisposable()

    fun onDestroy() {
        composite.dispose()
    }

    private fun getLeague() {
        composite.add(service.getAllLeague()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: LeagueResponse? ->
                    t?.leagueModels?.let {
                        saveLeague(it)
                    }
                }, {
                    view.showMessage("${it.message}")
                }))
    }

    private fun saveLeague(item: MutableList<LeagueModel>) {
        try {
            context.database.use {
                for (league in item) {
                    insert(League.TABLE_LEAGUE,
                            League.LEAGUE_ID to league.idLeague,
                            League.STR_LEAGUE to league.strLeague,
                            League.STR_SPORT to league.strSport)
                }
                view.showLeagueList()
            }
        } catch (e: SQLiteConstraintException) {
            view.showMessage(e.localizedMessage)
        }
    }

    fun getLeagueDB() {
        context.database.use {
            val result = select(League.TABLE_LEAGUE)
            val liga = result.parseList(classParser<League>())
            if (liga.isEmpty()) {
                getLeague()
            } else {
                view.showLeagueList()
            }
        }
    }
}