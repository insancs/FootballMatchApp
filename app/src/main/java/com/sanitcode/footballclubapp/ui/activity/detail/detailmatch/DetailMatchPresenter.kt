package com.sanitcode.footballclubapp.ui.activity.detail.detailmatch

import com.sanitcode.footballclubapp.api.ApiSportDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailMatchPresenter(private val view: DetailMatchView, private val service: ApiSportDB) {

    private val composite = CompositeDisposable()
    private val HOME = 1

    fun onDestroy() {
        composite.dispose()
    }

    fun getDetailMatch(id: String) {
        view.showLoading(true)
        composite.add(service.getMatchDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    view.showLoading(false)
                    t?.events?.let { view.showDetailMatch(it[0]) }
                }, {
                    view.showLoading(false)
                    view.showMessage("${it.message}")
                    view.showError(true)
                }))
    }

    fun getBadge(id: String, team: Int) {
        composite.add(service.getDetailTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    t?.teams?.let {
                        if (team == HOME)
                        //Me load image Badge Home
                            view.showBadgeImageHome(it[0].strTeamBadge ?: "")
                        else
                        //Me load image Badge Away
                            view.showBadgeImageAway(it[0].strTeamBadge ?: "")
                    }
                }, {

                }))
    }

}