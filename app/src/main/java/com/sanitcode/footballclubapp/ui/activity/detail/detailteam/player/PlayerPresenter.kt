package com.sanitcode.footballclubapp.ui.activity.detail.detailteam.player

import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.player.PlayerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PlayerPresenter(private val view: PlayerView, private val service: ApiSportDB) {

    private val composite = CompositeDisposable()

    fun onDestroy() {
        composite.dispose()
    }

    fun getPlayers(swipeShow: Boolean, id: String) {
        if (!swipeShow)
            view.showLoading(true)
        composite.add(service.getPlayerTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: PlayerResponse? ->
                    view.showLoading(false)
                    t?.player?.let {
                        view.showPlayerList(it)
                    }
                }, {
                    view.showLoading(false)
                    view.showMessage("${it.message}")
                }))
    }
}