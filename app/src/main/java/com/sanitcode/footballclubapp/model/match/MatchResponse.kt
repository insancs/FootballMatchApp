package com.sanitcode.footballclubapp.model.match

import com.google.gson.annotations.SerializedName

data class MatchResponse(@SerializedName("events") var events: MutableList<MatchModel>? = null)