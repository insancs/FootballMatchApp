package com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.fav_team_container
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.ui.activity.detail.detailteam.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class FavoriteTeamFragment : Fragment(), AnkoComponent<Context>, FavoriteTeamView {

    private var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var favTeamPresenter: FavoriteTeamPresenter
    private lateinit var favTeamAdapter: FavoriteTeamAdapter
    private lateinit var rvFavTeam: RecyclerView
    private lateinit var tvNoData: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favTeamAdapter = FavoriteTeamAdapter(context, favoriteTeams) { position -> getDetail(position) }
        favTeamPresenter = FavoriteTeamPresenter(this@FavoriteTeamFragment, context)
        rvFavTeam.adapter = favTeamAdapter
    }

    override fun onResume() {
        super.onResume()
        favoriteTeams.clear()
        favTeamPresenter.getTeams()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = fav_team_container
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                rvFavTeam = recyclerView {
                    id = R.id.rv_fav_team
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }

                tvNoData = textView {
                    text = ctx.getString(R.string.no_data)
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    margin = dip(16)
                    centerInParent()
                }
            }
        }
    }

    override fun showTeamList(data: List<FavoriteTeam>) {
        favoriteTeams.addAll(data)
        favTeamAdapter.notifyDataSetChanged()
    }

    override fun showNoData(show: Boolean) {
        when (show) {
        //Kondisi ketika di RecycleView belum ada item
            true -> {
                //RecycleView Favorite TeamModel tidak muncul
                rvFavTeam.visibility = GONE
                //TextView Nodata muncul
                tvNoData.visibility = VISIBLE
            }
        //Kondisi ketika di RecycleView sudah ada item
            false -> {
                //RecycleView favorite TeamModel muncul
                rvFavTeam.visibility = VISIBLE
                //TextView NoData tidak muncul
                tvNoData.visibility = GONE
            }
        }
    }

    private fun getDetail(position: Int) {
        val favTeams = favoriteTeams[position]
        context?.startActivity<DetailTeamActivity>(FavoriteTeam.TEAM_ID to favTeams.idTeam,
                FavoriteTeam.TEAM_NAME to favTeams.teamName,
                FavoriteTeam.FORMED_YEAR to favTeams.formedYear,
                FavoriteTeam.STADIUM to favTeams.stadium,
                FavoriteTeam.WEBSITE to favTeams.website,
                FavoriteTeam.DESC_EN to favTeams.descriptionEN,
                FavoriteTeam.TEAM_BADGE to favTeams.teamBadge,
                FavoriteTeam.TEAM_JERSEY to favTeams.teamJersey,
                FavoriteTeam.TEAM_FANART to favTeams.teamFanart1,
                FavoriteTeam.MANAGER to favTeams.manager,
                FavoriteTeam.CAPACITY to favTeams.capacity,
                FavoriteTeam.LOCATION to favTeams.location,
                FavoriteTeam.COUNTRY to favTeams.country,
                FavoriteTeam.STADIUM_DESC to favTeams.stadiumDesc,
                FavoriteTeam.STADIUM_THUMB to favTeams.stadiumThumb)
    }
}