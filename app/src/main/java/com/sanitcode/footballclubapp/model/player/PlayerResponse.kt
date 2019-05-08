package com.sanitcode.footballclubapp.model.player

import com.google.gson.annotations.SerializedName

data class PlayerResponse(@SerializedName("player") var player: MutableList<PlayerModel>? = null)