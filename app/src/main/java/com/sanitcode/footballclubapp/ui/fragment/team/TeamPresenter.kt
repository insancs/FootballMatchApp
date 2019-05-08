package com.sanitcode.footballclubapp.ui.fragment.team

import android.content.Context
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.db.database
import com.sanitcode.footballclubapp.model.team.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class TeamPresenter(private val view: TeamView, private val service: ApiSportDB,
                    private val context: Context?) {

    private val composite = CompositeDisposable()

    fun onDestroy() {
        composite.dispose()
    }

    fun getTeams(swipeShow: Boolean, id: String) {
        if (!swipeShow)
            view.showLoading(true)
        composite.add(service.getTeamAllLeague(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: TeamResponse? ->
                    view.showLoading(false)
                    t?.teams?.let {
                        view.showTeamList(it)
                    }
                }, {
                    view.showLoading(false)
                    view.showMessage("${it.message}")
                }))
    }

    fun getLeague() {
        context!!.database.use {
            val result = select(League.TABLE_LEAGUE)
            val league = result.parseList(classParser<League>())
            view.showLeagueList(league)
            if (league.isEmpty()) {
                view.showSpinner(false)
            } else {
                view.showSpinner(true)
            }
        }
    }
}