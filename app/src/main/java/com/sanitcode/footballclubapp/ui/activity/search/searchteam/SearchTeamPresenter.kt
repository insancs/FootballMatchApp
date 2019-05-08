package com.sanitcode.footballclubapp.ui.activity.search.searchteam

import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.team.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchTeamPresenter(private val view: SearchTeamView, private val service: ApiSportDB) {

    private val composite = CompositeDisposable()

    fun onDestroy() {
        composite.dispose()
    }

    fun getSearchTeams(id: String) {
        view.showLoading(true)
        composite.add(service.getSearchTeam(id)
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

}