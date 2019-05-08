package com.sanitcode.footballclubapp.db

data class FavoriteMatch(val id: Long?,
                         val eventId: String?,
                         val eventDate: String?,
                         val timeMatch: String?,
                         val homeId: String?,
                         val homeName: String?,
                         val homeScore: Int?,
                         val homeGoal: String?,
                         val lineupGk: String?,
                         val homeDefense: String?,
                         val homeMidfield: String?,
                         val homeForward: String?,
                         val homeSubstitutes: String?,
                         val homeFormation: String?,
                         val homeShots: String?,
                         val homeYellowCard: String?,
                         val homeRedCard: String?,
                         val awayId: String?,
                         val awayName: String?,
                         val awayScore: Int?,
                         val awayGoal: String?,
                         val awayGk: String?,
                         val awayDefense: String?,
                         val awayMidfield: String?,
                         val awayForward: String?,
                         val awaySubstitutes: String?,
                         val awayFormation: String?,
                         val awayShots: String?,
                         val awayYellowCard: String?,
                         val awayRedCard: String?,
                         val eventMatch: String?,
                         val leagueName: String?) {

    companion object {
        const val TABLE_MATCH: String = "TABLE_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val TIME_MATCH: String = "TIME_MATCH"
        const val EVENT_MATCH: String = "EVENT_MATCH"
        const val LEAGUE_NAME: String = "LEAGUE_NAME"
        //Home Column
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val HOME_GOAL: String = "HOME_GOAL"
        const val HOME_GK: String = "HOME_GK"
        const val HOME_DEFENSE: String = "HOME_DEFENSE"
        const val HOME_MIDFIELD: String = "HOME_MIDFIELD"
        const val HOME_FORWARD: String = "HOME_FORWARD"
        const val HOME_SUBSTITUTES: String = "HOME_SUBSTITUTES"
        const val HOME_FORMATION: String = "HOME_FORMATION"
        const val HOME_SHOTS: String = "HOME_SHOTS"
        const val HOME_YELLOW_CARD: String = "HOME_YELLOW_CARD"
        const val HOME_RED_CARD: String = "HOME_RED_CARD"
        //Away Column
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_GOAL: String = "AWAY_GOAL"
        const val AWAY_GK: String = "AWAY_GK"
        const val AWAY_DEFENSE: String = "AWAY_DEFENSE"
        const val AWAY_MIDFIELD: String = "AWAY_MIDFIELD"
        const val AWAY_FORWARD: String = "AWAY_FORWARD"
        const val AWAY_SUBSTITUTES: String = "AWAY_SUBSTITUTES"
        const val AWAY_FORMATION: String = "AWAY_FORMATION"
        const val AWAY_SHOTS: String = "AWAY_SHOTS"
        const val AWAY_YELLOW_CARD: String = "AWAY_YELLOW_CARD"
        const val AWAY_RED_CARD: String = "AWAY_RED_CARD"
    }
}