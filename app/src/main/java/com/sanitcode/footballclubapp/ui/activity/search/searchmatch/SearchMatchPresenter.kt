package com.sanitcode.footballclubapp.ui.activity.search.searchmatch

import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.response.SearchMatchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchMatchPresenter(private val view: SearchMatchView, private val service: ApiSportDB) {

    private val composite = CompositeDisposable()

    fun onDestroy() {
        composite.dispose()
    }

    fun getSearchMatch(id: String) {
        view.showLoading(true)
        composite.add(service.getSearchMatch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: SearchMatchResponse? ->
                    view.showLoading(false)
                    t?.events?.let {
                        view.showMatchList(it)
                    }
                }, {
                    view.showLoading(false)
                    view.showMessage("${it.message}")
                }))
    }
}