package com.sanitcode.footballclubapp.db

data class League(val id: Long?, val idLeague: String?, val strLeague: String?, val strSport: String?) {
    companion object {
        const val TABLE_LEAGUE: String = "TABLE_LEAGUE"
        const val ID: String = "ID_"
        const val LEAGUE_ID: String = "LEAGUE_ID"
        const val STR_LEAGUE: String = "STR_LEAGUE"
        const val STR_SPORT: String = "STR_SPORT"
    }
}