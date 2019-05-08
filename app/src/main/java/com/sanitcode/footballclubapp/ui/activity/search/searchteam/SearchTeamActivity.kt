package com.sanitcode.footballclubapp.ui.activity.search.searchteam

import android.R.id.home
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.ui.base.BaseActivity
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.model.team.TeamModel
import com.sanitcode.footballclubapp.ui.activity.detail.detailteam.DetailTeamActivity
import com.sanitcode.footballclubapp.ui.fragment.team.TeamAdapter
import kotlinx.android.synthetic.main.activity_search_main.*
import kotlinx.android.synthetic.main.content_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchTeamActivity : BaseActivity(), SearchTeamView {

    //Mendeklarasikan variabel
    private var teams: MutableList<TeamModel> = mutableListOf()
    private lateinit var searchTeamPresenter: SearchTeamPresenter
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_main)
        setSupportActionBar(toolbar_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchTeamPresenter = SearchTeamPresenter(this, ApiSportDB.instance)
        rv_search_list.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rv_search_list.layoutManager = layoutManager
        teamAdapter = TeamAdapter(this, teams) { position ->
            getDetail(position)
        }
        rv_search_list.adapter = teamAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_team, menu)

        val menuSearch = menu.findItem(R.id.search_team)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (menuSearch != null) {
            searchView = menuSearch.actionView as SearchView
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searchTeamPresenter.getSearchTeams(newText ?: "")
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean = true
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            home -> {
                back()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading(show: Boolean) {
        when (show) {
        //ProgressBar muncul
            true -> progressbar_search.visibility = View.VISIBLE
        //ProgressBar tidak muncul
            false -> progressbar_search.visibility = View.GONE
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showTeamList(data: MutableList<TeamModel>) {
        teams.clear()
        teams.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    private fun getDetail(position: Int) {
        val searchTeams = teams[position]
        startActivity<DetailTeamActivity>(FavoriteTeam.TEAM_ID to teams[position].idTeam,
                FavoriteTeam.TEAM_NAME to searchTeams.strTeam,
                FavoriteTeam.FORMED_YEAR to searchTeams.intFormedYear,
                FavoriteTeam.STADIUM to searchTeams.strStadium,
                FavoriteTeam.WEBSITE to searchTeams.strWebsite,
                FavoriteTeam.DESC_EN to searchTeams.strDescriptionEN,
                FavoriteTeam.TEAM_BADGE to searchTeams.strTeamBadge,
                FavoriteTeam.TEAM_JERSEY to searchTeams.strTeamJersey,
                FavoriteTeam.TEAM_FANART to searchTeams.strTeamFanart1,
                FavoriteTeam.MANAGER to searchTeams.strManager,
                FavoriteTeam.CAPACITY to searchTeams.intStadiumCapacity,
                FavoriteTeam.LOCATION to searchTeams.strStadiumLocation,
                FavoriteTeam.COUNTRY to searchTeams.strCountry,
                FavoriteTeam.STADIUM_DESC to searchTeams.strStadiumDescription,
                FavoriteTeam.STADIUM_THUMB to searchTeams.strStadiumThumb)
    }
}