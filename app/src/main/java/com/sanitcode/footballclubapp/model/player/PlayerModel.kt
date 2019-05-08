package com.sanitcode.footballclubapp.model.player

import com.google.gson.annotations.SerializedName

data class PlayerModel(
        @SerializedName("strPlayer") var strPlayer: String? = null,
        @SerializedName("strTeam") var strTeam: String? = null,
        @SerializedName("dateBorn") var dateBorn: String? = null,
        @SerializedName("dateSigned") var dateSigned: String? = null,
        @SerializedName("strBirthLocation") var strBirthLocation: String? = null,
        @SerializedName("strDescriptionEN") var strDescriptionEN: String? = null,
        @SerializedName("strGender") var strGender: String? = null,
        @SerializedName("strPosition") var strPosition: String? = null,
        @SerializedName("strHeight") var strHeight: String? = null,
        @SerializedName("strWeight") var strWeight: String? = null,
        @SerializedName("intLoved") var intLoved: String? = null,
        @SerializedName("strCutout") var strCutout: String? = null,
        @SerializedName("strSigning") var strSigning: String? = null,
        @SerializedName("strWage") var strWage: String? = null,
        @SerializedName("strFanart1") var strFanart1: String? = null,
        @SerializedName("strNationality") var strNationality: String? = null
)