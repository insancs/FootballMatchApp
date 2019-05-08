package com.sanitcode.footballclubapp.db

data class FavoriteTeam(val id: Long?,
                        val idTeam: String?,
                        val teamName: String?,
                        val formedYear: String?,
                        val stadium: String?,
                        val website: String?,
                        val descriptionEN: String?,
                        val teamBadge: String?,
                        val teamJersey: String?,
                        val teamFanart1: String?,
                        val manager: String?,
                        val capacity: String?,
                        val location: String?,
                        val country: String?,
                        val stadiumDesc: String?,
                        val stadiumThumb: String?) {
    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val FORMED_YEAR: String = "FORMED_YEAR"
        const val STADIUM: String = "STADIUM"
        const val WEBSITE: String = "WEBSITE"
        const val DESC_EN: String = "DESC_EN"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_JERSEY: String = "TEAM_JERSEY"
        const val TEAM_FANART: String = "TEAM_FANART"
        const val MANAGER: String = "MANAGER"
        const val CAPACITY: String = "CAPACITY"
        const val LOCATION: String = "LOCATION"
        const val COUNTRY: String = "COUNTRY"
        const val STADIUM_DESC: String = "STADIUM_DESC"
        const val STADIUM_THUMB: String = "STADIUM_THUMB"
    }
}