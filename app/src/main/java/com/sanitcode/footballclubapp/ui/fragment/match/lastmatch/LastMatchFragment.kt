package com.sanitcode.footballclubapp.ui.fragment.match.lastmatch

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.color.colorAccent
import com.sanitcode.footballclubapp.R.id.last_container
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.ui.activity.detail.detailmatch.DetailMatchActivity
import com.sanitcode.footballclubapp.ui.base.BaseFragment
import com.sanitcode.footballclubapp.ui.activity.league.SpinnerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment : BaseFragment(), MatchView, AnkoComponent<Context> {

    private var matches: MutableList<ItemModel> = mutableListOf()
    private lateinit var lastMatchPresenter: LastMatchPresenter
    private lateinit var lastMatchAdapter: LastMatchAdapter
    private lateinit var rvLastMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<League>
    private val spinerItem = mutableListOf<League>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lastMatchPresenter = LastMatchPresenter(this@LastMatchFragment, ApiSportDB.instance, context)
        lastMatchPresenter.getLeague()

        lastMatchAdapter = LastMatchAdapter(matches) { position ->
            getDetail(position)
        }

        rvLastMatch.adapter = lastMatchAdapter
        swipeRefresh.onRefresh {
            lastMatchPresenter.getMatch(true, getIdLeague())
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveLeague(spinerItem[position])
                lastMatchPresenter.getMatch(false, spinerItem[position].idLeague ?: "")
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
        lastMatchPresenter.onDestroy()
        super.onStop()
    }

    override fun showMatchList(data: MutableList<ItemModel>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        lastMatchAdapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = last_container
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            frameLayout {
                lparams(matchParent, wrapContent)

                spinner = spinner {
                    id = R.id.spinner_last
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
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvLastMatch = recyclerView {
                        id = R.id.rv_match
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
        context?.startActivity<DetailMatchActivity>("id" to matches[position].idEvent,
                "eventTime" to "past",
                "home" to matches[position].idHomeTeam,
                "away" to matches[position].idAwayTeam)
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