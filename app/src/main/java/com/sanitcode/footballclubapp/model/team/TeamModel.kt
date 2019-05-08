package com.sanitcode.footballclubapp.model.team

import com.google.gson.annotations.SerializedName

data class TeamModel(
        @SerializedName("idTeam") var idTeam: String? = null,
        @SerializedName("strTeam") var strTeam: String? = null,
        @SerializedName("intFormedYear") var intFormedYear: String? = null,
        @SerializedName("strManager") var strManager: String? = null,
        @SerializedName("strStadium") var strStadium: String? = null,
        @SerializedName("strStadiumThumb") var strStadiumThumb: String? = null,
        @SerializedName("strStadiumDescription") var strStadiumDescription: String? = null,
        @SerializedName("strStadiumLocation") var strStadiumLocation: String? = null,
        @SerializedName("intStadiumCapacity") var intStadiumCapacity: String? = null,
        @SerializedName("strWebsite") var strWebsite: String? = null,
        @SerializedName("strDescriptionEN") var strDescriptionEN: String? = null,
        @SerializedName("strGender") var strGender: String? = null,
        @SerializedName("strCountry") var strCountry: String? = null,
        @SerializedName("strTeamBadge") var strTeamBadge: String? = null,
        @SerializedName("strTeamJersey") var strTeamJersey: String? = null,
        @SerializedName("strTeamFanart1") var strTeamFanart1: String? = null,
        @SerializedName("strLocked") var strLocked: String? = null
)