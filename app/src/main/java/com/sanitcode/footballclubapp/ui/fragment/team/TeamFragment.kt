package com.sanitcode.footballclubapp.ui.fragment.team

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.rv_team
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.ui.base.BaseFragment
import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.model.team.TeamModel
import com.sanitcode.footballclubapp.ui.activity.detail.detailteam.DetailTeamActivity
import com.sanitcode.footballclubapp.ui.activity.league.SpinnerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class TeamFragment : BaseFragment(), TeamView, AnkoComponent<Context> {

    private var teams: MutableList<TeamModel> = mutableListOf()
    private lateinit var teamPresenter: TeamPresenter
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<League>
    private val spinerItem = mutableListOf<League>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamPresenter = TeamPresenter(this@TeamFragment, ApiSportDB.instance, context)
        teamPresenter.getLeague()

        teamAdapter = TeamAdapter(context, teams) { position ->
            getDetail(position)
        }

        listTeam.adapter = teamAdapter
        swipeRefresh.onRefresh {
            teamPresenter.getTeams(true, getIdLeague())
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveLeague(spinerItem[position])
                teamPresenter.getTeams(false, spinerItem[position].idLeague ?: "")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> progressBar.visibility = VISIBLE
            false -> progressBar.visibility = GONE
        }
    }

    override fun onStop() {
        teamPresenter.onDestroy()
        super.onStop()
    }

    override fun showTeamList(data: MutableList<TeamModel>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuSearch = menu?.findItem(R.id.search_team)
        menuSearch?.isVisible = true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = R.id.layout_team_list
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            frameLayout {
                lparams(matchParent, wrapContent)

                spinner = spinner {
                    id = R.id.spinner_team
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(5)
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(android.R.color.holo_red_light,
                        android.R.color.holo_green_light,
                        android.R.color.holo_blue_light,
                        android.R.color.holo_orange_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = rv_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerInParent()
                    }
                }
            }
        }
    }

    private fun getDetail(position: Int) {
        val teamDetail = teams[position]
        context?.startActivity<DetailTeamActivity>(FavoriteTeam.TEAM_ID to teams[position].idTeam,
                FavoriteTeam.TEAM_NAME to teamDetail.strTeam,
                FavoriteTeam.FORMED_YEAR to teamDetail.intFormedYear,
                FavoriteTeam.STADIUM to teamDetail.strStadium,
                FavoriteTeam.WEBSITE to teamDetail.strWebsite,
                FavoriteTeam.DESC_EN to teamDetail.strDescriptionEN,
                FavoriteTeam.TEAM_BADGE to teamDetail.strTeamBadge,
                FavoriteTeam.TEAM_JERSEY to teamDetail.strTeamJersey,
                FavoriteTeam.TEAM_FANART to teamDetail.strTeamFanart1,
                FavoriteTeam.MANAGER to teamDetail.strManager,
                FavoriteTeam.CAPACITY to teamDetail.intStadiumCapacity,
                FavoriteTeam.LOCATION to teamDetail.strStadiumLocation,
                FavoriteTeam.COUNTRY to teamDetail.strCountry,
                FavoriteTeam.STADIUM_DESC to teams[position].strStadiumDescription,
                FavoriteTeam.STADIUM_THUMB to teams[position].strStadiumThumb)
    }

    override fun showLeagueList(data: List<League>) {
        spinerItem.addAll(data)
        spinnerAdapter = SpinnerAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinerItem)
        spinner.adapter = spinnerAdapter
        spinnerAdapter.notifyDataSetChanged()
    }

    override fun showSpinner(show: Boolean) {
        when (show) {
            true -> spinner.visibility = VISIBLE
            false -> spinner.visibility = GONE
        }
    }
}