package com.sanitcode.footballclubapp.api

import com.sanitcode.footballclubapp.BuildConfig
import com.sanitcode.footballclubapp.model.league.LeagueResponse
import com.sanitcode.footballclubapp.model.match.MatchResponse
import com.sanitcode.footballclubapp.model.player.PlayerResponse
import com.sanitcode.footballclubapp.model.response.ScheduleResponse
import com.sanitcode.footballclubapp.model.response.SearchMatchResponse
import com.sanitcode.footballclubapp.model.team.TeamResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

interface ApiSportDB {

    companion object {
        val instance: ApiSportDB by lazy {
            val okHttpClientBuilder = OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BASIC
                okHttpClientBuilder.addInterceptor(logging)
            }
            val retrofit = Retrofit.Builder()
                    .client(okHttpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
            retrofit.create(ApiSportDB::class.java)
        }
    }

    @GET("api/v1/json/${BuildConfig.API_KEY}/eventsnextleague.php")
    fun getNextMatch(@Query("id") idTeam: String): Observable<ScheduleResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/eventspastleague.php")
    fun getLastMatch(@Query("id") idTeam: String): Observable<ScheduleResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/lookup_all_teams.php")
    fun getTeamAllLeague(@Query("id") idTeam: String): Observable<TeamResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/lookup_all_players.php")
    fun getPlayerTeam(@Query("id") idTeam: String): Observable<PlayerResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/lookupteam.php")
    fun getDetailTeam(@Query("id") idTeam: String): Observable<TeamResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/searchteams.php?s=Soccer")
    fun getSearchTeam(@Query("t") idTeam: String): Observable<TeamResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/searchevents.php")
    fun getSearchMatch(@Query("e") idTeam: String): Observable<SearchMatchResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/lookupevent.php")
    fun getMatchDetail(@Query("id") idMatch: String): Observable<MatchResponse>

    @GET("api/v1/json/${BuildConfig.API_KEY}/search_all_leagues.php?s=Soccer")
    fun getAllLeague(): Observable<LeagueResponse>
}