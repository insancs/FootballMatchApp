package com.sanitcode.footballclubapp.ui.activity.detail.detailteam.player

import com.sanitcode.footballclubapp.model.player.PlayerModel

interface PlayerView {
    fun showLoading(show: Boolean)
    fun showMessage(message: String)
    fun showPlayerList(data: MutableList<PlayerModel>)
}