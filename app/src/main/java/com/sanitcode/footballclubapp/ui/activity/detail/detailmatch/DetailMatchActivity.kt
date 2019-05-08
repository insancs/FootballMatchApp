package com.sanitcode.footballclubapp.ui.activity.detail.detailmatch

import android.annotation.SuppressLint
import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.sanitcode.footballclubapp.db.FavoriteMatch
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.drawable.*
import com.sanitcode.footballclubapp.R.id.add_to_favorite
import com.sanitcode.footballclubapp.R.menu.favorite
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.db.database
import com.sanitcode.footballclubapp.model.match.MatchModel
import com.sanitcode.footballclubapp.util.*
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.detail_match_card.*
import kotlinx.android.synthetic.main.detail_match_goal.*
import kotlinx.android.synthetic.main.detail_match_lineup.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var match: MatchModel
    private var idEvent: String = ""
    private var event: String = ""
    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        idEvent = intent.getStringExtra("id")
        event = intent.getStringExtra("eventTime")
        presenter = DetailMatchPresenter(this, ApiSportDB.instance)
        presenter.getBadge(intent.getStringExtra("home"), 1)
        presenter.getBadge(intent.getStringExtra("away"), 2)
        favoriteState()

        when (event) {
            "past" -> setupLastMatch()
            "next" -> setupNextMatch()
            "local" -> setupMatchDB()
        }
        btn_try_again.setOnClickListener {
            getData()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite, menu)
        menuItem = menu
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_OK)
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavourite) removeFavorite() else addToFavorite()
                isFavourite = !isFavourite
                setFavoriteIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData() {
        //Layout dengan id ll_error tidak muncul
        layout_error.visibility = View.GONE
        //Mengambil data dari DetailMatchPresenter
        presenter.getDetailMatch(idEvent)
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> {
                //ProgressBar dengan id = pb_match_detail muncul
                progressbar_match.visibility = View.VISIBLE

                //layout dengan id = detail_match_goal tidak muncul
                detail_match_goal.visibility = View.GONE

                //layout dengan id = detail_match_card muncul
                detail_match_card.visibility = View.GONE

                //layout detail_match_lineup tidak muncul
                detail_match_lineup.visibility = View.GONE
            }
            false -> {
                //ProgressBar tidak muncul
                progressbar_match.visibility = View.GONE

                //layout dengan id = detail_match_goal muncul
                detail_match_goal.visibility = View.VISIBLE

                //layout dengan id = detail_match_goal muncul
                detail_match_card.visibility = View.VISIBLE

                //layout dengan id = detail_match_lineup tidak muncul
                detail_match_lineup.visibility = View.VISIBLE
            }
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    @SuppressLint("SetTextI18n")
    override fun showDetailMatch(data: MatchModel) {
        //Men set ke detail match ke setiap TextView dengan masing-masing id
        match = data
        tv_home_team.text = data.strHomeTeam
        tv_score.text = "${data.intHomeScore} - ${data.intAwayScore}"
        tv_date.text = formatDate(data.dateEvent ?: "")
        tv_time.text = toGMTTime(data.strTime ?: "")
        tv_league.text = data.strLeague
        tv_home_goal.text = data.strHomeGoalDetails?.changeLine()
        tv_home_gk.text = data.strHomeLineupGoalkeeper?.changeLine()
        tv_home_def.text = data.strHomeLineupDefense?.changeLine()
        tv_home_mif.text = data.strHomeLineupMidfield?.changeLine()
        tv_home_fwd.text = data.strHomeLineupForward?.changeLine()
        tv_home_formation.text = data.strHomeFormation
        tv_home_shots.text = data.intHomeShots
        tv_yellow_home.text = data.strHomeYellowCards?.changeLine()
        tv_red_home.text = data.strHomeRedCards?.changeLine()
        tv_home_subtitutes.text = data.strHomeLineupSubstitutes
        tv_away_team.text = data.strAwayTeam
        tv_away_goal.text = data.strAwayGoalDetails?.changeLine()
        tv_away_gk.text = data.strAwayLineupGoalkeeper?.changeLine()
        tv_away_def.text = data.strAwayLineupDefense?.changeLine()
        tv_away_mid.text = data.strAwayLineupMidfield?.changeLine()
        tv_away_fwd.text = data.strAwayLineupForward?.changeLine()
        tv_away_formation.text = data.strAwayFormation
        tv_away_shots.text = data.intAwayShots
        tv_yellow_away.text = data.strAwayYellowCards?.changeLine()
        tv_red_away.text = data.strAwayRedCards?.changeLine()
        tv_away_subtitutes.text = data.strAwayLineupSubstitutes
    }

    override fun showError(show: Boolean) {
        when (show) {
            true -> {
                //TextView dengan id = ll_error muncul
                layout_error.visibility = View.VISIBLE

                //layout detail_match_goal muncul
                detail_match_goal.visibility = View.GONE

                //layout detail_match_goal muncul
                detail_match_card.visibility = View.GONE

                //layout detail_match_lineup muncul
                detail_match_lineup.visibility = View.GONE
            }
            false -> {
                //id ll_error tidak muncul
                layout_error.visibility = View.GONE

                //layout detail_match_goal tidak muncul
                detail_match_goal.visibility = View.VISIBLE

                //layout detail_match_goal tidak muncul
                detail_match_card.visibility = View.VISIBLE

                //layout detail_match_lineup tidak muncul
                detail_match_lineup.visibility = View.VISIBLE
            }
        }
    }

    private fun setupLastMatch() {
        getData()
        //layout dengan id = layout_goal muncul
        layout_goal.visibility = View.VISIBLE
        //layout dengan id = layout_detail_card muncul
        layout_detail_card.visibility = View.VISIBLE
        //layout dengan id = layout_detail_lineup muncul
        layout_detail_lineup.visibility = View.VISIBLE
    }

    private fun setupNextMatch() {
        //Men set string/versus ke TextView dengan id = tv_skor_detail
        tv_score.text = getString(R.string.versus)

        //Hide ActionBar
        if (supportActionBar != null) {
            supportActionBar!!.hide();
        }

        //Layout dengan id = layout_goal tidak muncul
        layout_goal.visibility = View.GONE

        //Layout dengan id = detail_match_card tidak muncul
        detail_match_card.visibility = View.GONE

        //Layout dengan id = detail_match_card tidak muncul
        detail_match_lineup.visibility = View.GONE

        //Men set string/highlight_coming_soon ke TextView dengan id = tv_highlight
        tv_highlight.text = getString(R.string.highlight_coming_soon)
        tv_home_team.text = intent.getStringExtra("homeTeam")
        tv_away_team.text = intent.getStringExtra("awayTeam")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        tv_league.text = intent.getStringExtra("league")
        tv_date.text = formatDate(date)
        tv_time.text = toGMTTime(time)
    }

    private fun setupMatchDB()
    {
        database.use {
            val result = select(FavoriteMatch.TABLE_MATCH)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent)
            val favoriteList = result.parseList(classParser<FavoriteMatch>())
            val favorite = favoriteList[0]
            setupFavorite(favorite)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_MATCH)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            //State ketika match sudah ditambahan ke FavoriteMatch list atau belum
            isFavourite = !favorite.isEmpty()
        }
    }

    private fun setFavoriteIcon() {
        //Berubah warna ketika ditambahkan ke FavoriteMatch List MatchModel
        if (isFavourite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite)
        else
        //Berubah warna ketika dihapus ke FavoriteMatch List MatchModel
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_border)
    }

    private fun removeFavorite() {
        try {
            database.use {
                //Menghapus TABLE_MATCH
                delete(FavoriteMatch.TABLE_MATCH, "(EVENT_ID = {id})",
                        "id" to idEvent)
            }
            //Muncul snackbar dengan text "Removed to favorite" ketika macth dihapus dari FavoriteMatch List
            layout_match_detail.snackbar(getString(R.string.removedb)).show()
        } catch (e: SQLiteConstraintException) {
            layout_match_detail.snackbar(e.localizedMessage).show()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                //Menambahkan TABLE_MATCH
                insert(FavoriteMatch.TABLE_MATCH,
                        FavoriteMatch.EVENT_ID to idEvent,
                        FavoriteMatch.EVENT_DATE to match.dateEvent,
                        FavoriteMatch.TIME_MATCH to match.strTime,
                        FavoriteMatch.HOME_ID to match.idHomeTeam,
                        FavoriteMatch.HOME_NAME to match.strHomeTeam,
                        FavoriteMatch.HOME_SCORE to match.intHomeScore,
                        FavoriteMatch.HOME_GOAL to match.strHomeGoalDetails,
                        FavoriteMatch.HOME_GK to match.strHomeLineupGoalkeeper,
                        FavoriteMatch.HOME_DEFENSE to match.strHomeLineupDefense,
                        FavoriteMatch.HOME_MIDFIELD to match.strHomeLineupMidfield,
                        FavoriteMatch.HOME_FORWARD to match.strHomeLineupForward,
                        FavoriteMatch.HOME_SUBSTITUTES to match.strHomeLineupSubstitutes,
                        FavoriteMatch.HOME_FORMATION to match.strHomeFormation,
                        FavoriteMatch.HOME_SHOTS to match.intHomeShots,
                        FavoriteMatch.HOME_YELLOW_CARD to match.strHomeYellowCards,
                        FavoriteMatch.HOME_RED_CARD to match.strHomeRedCards,
                        FavoriteMatch.AWAY_ID to match.idAwayTeam,
                        FavoriteMatch.AWAY_NAME to match.strAwayTeam,
                        FavoriteMatch.AWAY_SCORE to match.intAwayScore,
                        FavoriteMatch.AWAY_GOAL to match.strAwayGoalDetails,
                        FavoriteMatch.AWAY_GK to match.strAwayLineupGoalkeeper,
                        FavoriteMatch.AWAY_DEFENSE to match.strAwayLineupDefense,
                        FavoriteMatch.AWAY_MIDFIELD to match.strAwayLineupMidfield,
                        FavoriteMatch.AWAY_FORWARD to match.strAwayLineupForward,
                        FavoriteMatch.AWAY_SUBSTITUTES to match.strAwayLineupSubstitutes,
                        FavoriteMatch.AWAY_FORMATION to match.strAwayFormation,
                        FavoriteMatch.AWAY_SHOTS to match.intAwayScore,
                        FavoriteMatch.AWAY_YELLOW_CARD to match.strAwayYellowCards,
                        FavoriteMatch.AWAY_RED_CARD to match.strAwayRedCards,
                        FavoriteMatch.EVENT_MATCH to event,
                        FavoriteMatch.LEAGUE_NAME to match.strLeague)
            }
            //Muncul snackbar dengan text dari resource string/added ketika macth ditambahkan ke FavoriteMatch List
            layout_match_detail.snackbar(getString(R.string.added)).show()
        } catch (e: SQLiteConstraintException) {
            layout_match_detail.snackbar(e.localizedMessage).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupFavorite(fav: FavoriteMatch) {
        when (fav.eventMatch) {
            "past" -> {
                tv_home_team.text = fav.homeName
                tv_away_team.text = fav.awayName
                tv_score.text = "${fav.homeScore} - ${fav.awayScore}"
                tv_date.text = formatDate(fav.eventDate ?: "")
                tv_time.text = toGMTTime(fav.timeMatch ?: "")
                tv_league.text = fav.leagueName
                tv_home_goal.text = fav.homeGoal?.replace(";", "")
                tv_away_goal.text = fav.awayGoal?.replace(";", "")
                tv_home_gk.text = fav.lineupGk?.replace(";", "")
                tv_away_gk.text = fav.awayGk?.replace(";", "")
                tv_home_def.text = fav.homeDefense?.replace(";", "")
                tv_away_def.text = fav.awayDefense?.replace(";", "")
                tv_home_mif.text = fav.homeMidfield?.replace(";", "")
                tv_away_mid.text = fav.awayMidfield?.replace(";", "")
                tv_home_fwd.text = fav.homeForward?.replace(";", "")
                tv_away_fwd.text = fav.awayForward?.replace(";", "")
                tv_home_subtitutes.text = fav.homeSubstitutes?.replace(";", "")
                tv_away_subtitutes.text = fav.awaySubstitutes?.replace(";", "")
                tv_home_shots.text = fav.homeShots?.replace(";", "")
                tv_away_shots.text = fav.awayShots?.replace(";", "")
                tv_home_formation.text = fav.homeFormation
                tv_away_formation.text = fav.awayFormation
                tv_yellow_home.text = fav.homeYellowCard?.replace(";", "")
                tv_yellow_away.text = fav.awayYellowCard?.replace(";", "")
                tv_red_home.text = fav.homeRedCard?.replace(";", "")
                tv_red_away.text = fav.awayRedCard?.replace(";", "")
            }
        }
    }

    override fun showBadgeImageHome(string: String) {
        //Me load image Badge Home
        loadImage(this, string, iv_home_badge)
    }

    override fun showBadgeImageAway(string: String) {
        //Me load image Badge Away
        loadImage(this, string, iv_away_badge)
    }
}
