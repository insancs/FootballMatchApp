package com.sanitcode.footballclubapp.model.team

import com.google.gson.annotations.SerializedName

data class TeamResponse(@SerializedName("teams") var teams: MutableList<TeamModel>? = null)