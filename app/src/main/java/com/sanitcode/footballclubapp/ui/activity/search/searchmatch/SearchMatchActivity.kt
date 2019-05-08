package com.sanitcode.footballclubapp.ui.activity.search.searchmatch

import android.R.id.home
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sanitcode.footballclubapp.ui.fragment.match.lastmatch.LastMatchAdapter
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.util.checkBeforeDate
import com.sanitcode.footballclubapp.ui.activity.detail.detailmatch.DetailMatchActivity
import com.sanitcode.footballclubapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_main.*
import kotlinx.android.synthetic.main.content_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchMatchActivity : BaseActivity(), SearchMatchView {

    //Mendeklarasikan variabel
    private var matches: MutableList<ItemModel> = mutableListOf()
    private lateinit var searchMatchPresenter: SearchMatchPresenter
    private lateinit var lastMatchAdapter: LastMatchAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_main)

        setSupportActionBar(toolbar_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchMatchPresenter = SearchMatchPresenter(this, ApiSportDB.instance)
        rv_search_list.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rv_search_list.layoutManager = layoutManager
        lastMatchAdapter = LastMatchAdapter(matches) { position ->
            getSearchMatch(position)
        }
        rv_search_list.adapter = lastMatchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_match, menu)

        val menuSearch = menu.findItem(R.id.search_match)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (menuSearch != null) {
            searchView = menuSearch.actionView as SearchView
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searchMatchPresenter.getSearchMatch(newText ?: "")
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

    override fun showMatchList(data: MutableList<ItemModel>) {
        matches.clear()
        matches.addAll(data)
        lastMatchAdapter.notifyDataSetChanged()
    }

    private fun getSearchMatch(position: Int) {
        val match = matches[position]
        //Kondisi ketika idHomeTeam dan idAwayTeam bernilai null
        if (!match.idHomeTeam.isNullOrEmpty() || !match.idAwayTeam.isNullOrEmpty()) {
            if (checkBeforeDate(match.dateEvent ?: "")) {
                startActivity<DetailMatchActivity>("id" to match.idEvent,
                        "eventTime" to "next",
                        "home" to match.idHomeTeam,
                        "away" to match.idAwayTeam,
                        "homeTeam" to match.strHomeTeam,
                        "awayTeam" to match.strAwayTeam,
                        "date" to match.dateEvent,
                        "league" to match.strLeague)
            } else {
                startActivity<DetailMatchActivity>("id" to match.idEvent,
                        "eventTime" to "past",
                        "home" to match.idHomeTeam,
                        "away" to match.idAwayTeam)
            }
        }
    }

}