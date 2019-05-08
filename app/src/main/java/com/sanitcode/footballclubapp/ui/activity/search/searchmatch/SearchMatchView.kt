package com.sanitcode.footballclubapp.ui.activity.search.searchmatch

import com.sanitcode.footballclubapp.model.ItemModel

interface SearchMatchView {
    fun showLoading(show: Boolean)
    fun showMessage(message: String)
    fun showMatchList(data: MutableList<ItemModel>)
}