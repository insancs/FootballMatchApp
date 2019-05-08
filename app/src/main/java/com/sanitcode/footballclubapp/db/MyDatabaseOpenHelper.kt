package com.sanitcode.footballclubapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx,
        "Favorite.db", null) {

    companion object {
        private var openHelper: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (openHelper == null) {
                openHelper = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return openHelper as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatch.TABLE_MATCH, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
                FavoriteMatch.EVENT_DATE to TEXT,
                FavoriteMatch.TIME_MATCH to TEXT,
                FavoriteMatch.HOME_ID to TEXT,
                FavoriteMatch.HOME_NAME to TEXT,
                FavoriteMatch.HOME_SCORE to INTEGER,
                FavoriteMatch.HOME_GOAL to TEXT,
                FavoriteMatch.HOME_GK to TEXT,
                FavoriteMatch.HOME_DEFENSE to TEXT,
                FavoriteMatch.HOME_MIDFIELD to TEXT,
                FavoriteMatch.HOME_FORWARD to TEXT,
                FavoriteMatch.HOME_SUBSTITUTES to TEXT,
                FavoriteMatch.HOME_FORMATION to TEXT,
                FavoriteMatch.HOME_SHOTS to TEXT,
                FavoriteMatch.HOME_YELLOW_CARD to TEXT,
                FavoriteMatch.HOME_RED_CARD to TEXT,
                FavoriteMatch.AWAY_ID to TEXT,
                FavoriteMatch.AWAY_NAME to TEXT,
                FavoriteMatch.AWAY_SCORE to INTEGER,
                FavoriteMatch.AWAY_GOAL to TEXT,
                FavoriteMatch.AWAY_GK to TEXT,
                FavoriteMatch.AWAY_DEFENSE to TEXT,
                FavoriteMatch.AWAY_MIDFIELD to TEXT,
                FavoriteMatch.AWAY_FORWARD to TEXT,
                FavoriteMatch.AWAY_SUBSTITUTES to TEXT,
                FavoriteMatch.AWAY_FORMATION to TEXT,
                FavoriteMatch.AWAY_SHOTS to TEXT,
                FavoriteMatch.AWAY_YELLOW_CARD to TEXT,
                FavoriteMatch.AWAY_RED_CARD to TEXT,
                FavoriteMatch.EVENT_MATCH to TEXT,
                FavoriteMatch.LEAGUE_NAME to TEXT)
        db.createTable(League.TABLE_LEAGUE, true,
                League.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                League.LEAGUE_ID to TEXT + UNIQUE,
                League.STR_LEAGUE to TEXT,
                League.STR_SPORT to TEXT)

        db.createTable(FavoriteTeam.TABLE_TEAM, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.FORMED_YEAR to TEXT,
                FavoriteTeam.STADIUM to TEXT,
                FavoriteTeam.WEBSITE to TEXT,
                FavoriteTeam.DESC_EN to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT,
                FavoriteTeam.TEAM_JERSEY to TEXT,
                FavoriteTeam.TEAM_FANART to TEXT,
                FavoriteTeam.MANAGER to TEXT,
                FavoriteTeam.CAPACITY to TEXT,
                FavoriteTeam.LOCATION to TEXT,
                FavoriteTeam.COUNTRY to TEXT,
                FavoriteTeam.STADIUM_DESC to TEXT,
                FavoriteTeam.STADIUM_THUMB to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_MATCH, true)
        db.dropTable(League.TABLE_LEAGUE, true)
        db.dropTable(FavoriteTeam.TABLE_TEAM, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)

