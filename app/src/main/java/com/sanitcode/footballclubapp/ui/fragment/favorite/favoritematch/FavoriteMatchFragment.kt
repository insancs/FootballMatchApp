package com.sanitcode.footballclubapp.ui.fragment.favorite.favoritematch

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.db.FavoriteMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class FavoriteMatchFragment : Fragment(), AnkoComponent<Context>, FavoriteMatchView {

    private var favMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var favMatchPresenter: FavoriteMatchPresenter
    private lateinit var favMatchAdapter: FavoriteMatchAdapter
    private lateinit var rvFavoriteMatch: RecyclerView
    private lateinit var tvNoData: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favMatchAdapter = FavoriteMatchAdapter(context, favMatches)
        favMatchPresenter = FavoriteMatchPresenter(this@FavoriteMatchFragment, context)
        rvFavoriteMatch.adapter = favMatchAdapter
    }

    override fun onResume() {
        super.onResume()
        favMatches.clear()
        favMatchPresenter.getMatch()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = fav_match_container
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                rvFavoriteMatch = recyclerView {
                    id = rv_fav_match
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

    override fun showMatchList(data: List<FavoriteMatch>) {
        favMatches.addAll(data)
        favMatchAdapter.notifyDataSetChanged()
    }

    override fun showNoData(show: Boolean) {
        when (show) {
            true -> {
                //RecycleView favorite MatchModel tidak muncul
                rvFavoriteMatch.visibility = View.GONE
                //TextView NoData muncul
                tvNoData.visibility = View.VISIBLE
            }
            false -> {
                //RecycleView favorite MatchModel muncul
                rvFavoriteMatch.visibility = View.VISIBLE
                //TextView NoData  tidak muncul
                tvNoData.visibility = View.GONE
            }
        }
    }

}