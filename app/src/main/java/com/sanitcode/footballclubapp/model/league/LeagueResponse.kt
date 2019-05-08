package com.sanitcode.footballclubapp.model.league

import com.google.gson.annotations.SerializedName

data class LeagueResponse(@SerializedName("countrys") var leagueModels: MutableList<LeagueModel>? = null)