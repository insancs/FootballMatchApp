package com.sanitcode.footballclubapp.ui.fragment.match.nextmatch

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.ui.fragment.match.lastmatch.MatchView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.next_container
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
import java.util.*

class NextMatchFragment : BaseFragment(), MatchView, AnkoComponent<Context> {

    private var matches: MutableList<ItemModel> = mutableListOf()
    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var nextMatchAdapter: NextMatchAdapter
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<League>
    private val spinerItem = mutableListOf<League>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nextMatchPresenter = NextMatchPresenter(this@NextMatchFragment, ApiSportDB.instance, context)
        nextMatchPresenter.getLeague()

        nextMatchAdapter = NextMatchAdapter(matches, { position ->
            getDetail(position)
        }, { position ->
            val item = matches[position]
            goEvent(item)
        })

        rvNextMatch.adapter = nextMatchAdapter
        swipeRefresh.onRefresh {
            nextMatchPresenter.getMatch(true, getIdLeague())
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveLeague(spinerItem[position])
                nextMatchPresenter.getMatch(false, spinerItem[position].idLeague ?: "")
            }
        }
    }

    override fun onStop() {
        nextMatchPresenter.onDestroy()
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    override fun showMatchList(data: MutableList<ItemModel>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        nextMatchAdapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = next_container
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            frameLayout {
                lparams(matchParent, wrapContent)

                spinner = spinner {
                    id = R.id.spinner_next
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
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvNextMatch = recyclerView {
                        id = R.id.rv_next_match
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
                "eventTime" to "next",
                "home" to matches[position].idHomeTeam,
                "away" to matches[position].idAwayTeam,
                "homeTeam" to matches[position].strHomeTeam,
                "awayTeam" to matches[position].strAwayTeam,
                "date" to matches[position].dateEvent,
                "league" to matches[position].strLeague)
    }

    override fun showLeagueList(data: List<League>) {
        spinerItem.addAll(data)
        spinnerAdapter = SpinnerAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinerItem)
        spinner.adapter = spinnerAdapter
        spinnerAdapter.notifyDataSetChanged()
    }

    override fun showSpinner(show: Boolean) {
        when (show) {
            true -> spinner.visibility = View.VISIBLE
            false -> spinner.visibility = View.GONE
        }
    }

    private fun goEvent(item: ItemModel) {
        val date: List<String> = item.dateEvent!!.split("-")
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, "${item.strFilename}")
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "${item.strHomeTeam}")
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "${item.strFilename}")
        val calDate = GregorianCalendar(date[0].toInt(), date[1].replace("0", "").toInt() - 1, date[2].toInt())
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        startActivity(intent)
    }

}