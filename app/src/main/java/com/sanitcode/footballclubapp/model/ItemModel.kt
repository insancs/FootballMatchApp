package com.sanitcode.footballclubapp.model

import com.google.gson.annotations.SerializedName

data class ItemModel(
        @SerializedName("idEvent") var idEvent: String? = null,
        @SerializedName("strFilename") var strFilename: String? = null,
        @SerializedName("strLeague") var strLeague: String? = null,
        @SerializedName("strHomeTeam") var strHomeTeam: String? = null,
        @SerializedName("strAwayTeam") var strAwayTeam: String? = null,
        @SerializedName("intHomeScore") var intHomeScore: String? = null,
        @SerializedName("intAwayScore") var intAwayScore: String? = null,
        @SerializedName("dateEvent") var dateEvent: String? = null,
        @SerializedName("idHomeTeam") var idHomeTeam: String? = null,
        @SerializedName("idAwayTeam") var idAwayTeam: String? = null,
        @SerializedName("strLocked") var strLocked: String? = null
)