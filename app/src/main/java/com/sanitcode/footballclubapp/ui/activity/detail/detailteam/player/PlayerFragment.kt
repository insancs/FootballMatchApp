package com.sanitcode.footballclubapp.ui.activity.detail.detailteam.player

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.sanitcode.footballclubapp.R.color.colorAccent
import com.sanitcode.footballclubapp.R.id.rv_players
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.ui.base.BaseFragment
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.model.player.PlayerModel
import com.sanitcode.footballclubapp.util.Util
import com.sanitcode.footballclubapp.ui.activity.detail.detailplayer.DetailPlayerActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class PlayerFragment : BaseFragment(), PlayerView, AnkoComponent<Context> {

    private var players: MutableList<PlayerModel> = mutableListOf()
    private lateinit var playerPresenter: PlayerPresenter
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var rvPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        playerPresenter = PlayerPresenter(this@PlayerFragment, ApiSportDB.instance)
        playerPresenter.getPlayers(false, activity?.intent?.getStringExtra(FavoriteTeam.TEAM_ID) ?: "")

        playerAdapter = PlayerAdapter(context, players) { position ->
            getDetail(position)
        }

        rvPlayer.adapter = playerAdapter
        swipeRefresh.onRefresh {
            playerPresenter.getPlayers(true, activity?.intent?.getStringExtra(FavoriteTeam.TEAM_ID) ?: "")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    override fun onStop() {
        playerPresenter.onDestroy()
        super.onStop()
    }

    override fun showPlayerList(data: MutableList<PlayerModel>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        playerAdapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    rvPlayer = recyclerView {
                        id = rv_players
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
        val player = players[position]
        context?.startActivity<DetailPlayerActivity>(Util.NAME_PLAYER to player.strPlayer,
                Util.COVER_PLAYER to player.strFanart1,
                Util.BADGE_PLAYER to player.strCutout,
                Util.POSITION_PLAYER to player.strPosition,
                Util.NAME_TEAM to player.strTeam,
                Util.DATE_BORN to player.dateBorn,
                Util.DATE_LOCATION to player.strBirthLocation,
                Util.GENDER to player.strGender,
                Util.NATIONALITY to player.strNationality,
                Util.HEIGHT_PLAYER to player.strHeight,
                Util.WEIGHT_PLAYER to player.strWeight,
                Util.LOVED to player.intLoved,
                Util.DATE_SIGNED to player.dateSigned,
                Util.BUDGET to player.strSigning,
                Util.INCOME_PLAYER to player.strWage,
                Util.DESC_PLAYER to player.strDescriptionEN)
    }
}