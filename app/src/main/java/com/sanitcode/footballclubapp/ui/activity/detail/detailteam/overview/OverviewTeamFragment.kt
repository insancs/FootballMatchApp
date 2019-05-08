package com.sanitcode.footballclubapp.ui.activity.detail.detailteam.overview

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.ui.base.BaseFragment
import com.sanitcode.footballclubapp.db.FavoriteTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class OverviewTeamFragment : BaseFragment(), AnkoComponent<Context> {

    private lateinit var tvTeamDesc: TextView
    private lateinit var tvManager: TextView
    private lateinit var tvStadium: TextView
    private lateinit var tvBornTeam: TextView
    private lateinit var tvStadiumDesc: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCapacity: TextView
    private lateinit var tvCountry: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvBornTeam.text = activity?.intent?.getStringExtra(FavoriteTeam.FORMED_YEAR)
        tvManager.text = activity?.intent?.getStringExtra(FavoriteTeam.MANAGER)
        tvStadium.text = activity?.intent?.getStringExtra(FavoriteTeam.STADIUM)
        tvCapacity.text = activity?.intent?.getStringExtra(FavoriteTeam.CAPACITY)
        tvLocation.text = activity?.intent?.getStringExtra(FavoriteTeam.LOCATION)
        tvCountry.text = activity?.intent?.getStringExtra(FavoriteTeam.COUNTRY)
        tvStadiumDesc.text = activity?.intent?.getStringExtra(FavoriteTeam.STADIUM_DESC)
        tvTeamDesc.text = activity?.intent?.getStringExtra(FavoriteTeam.DESC_EN)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.context!!))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        nestedScrollView {
            lparams(matchParent, matchParent)

            verticalLayout {
                lparams(wrapContent, wrapContent)

                tableLayout {
                    tableRow {
                        textView(R.string.born_team) {
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams(width = wrapContent, height = wrapContent)

                        tvBornTeam = textView {
                            id = tv_born_team
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(12)
                    }

                    tableRow {
                        textView(R.string.name_manager) {
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent)
                        tvManager = textView {
                            id = tv_manager
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(16)
                    }

                    tableRow {
                        textView(R.string.stadium) {
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent)
                        tvStadium = textView {
                            id = tv_stadium
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(12)
                    }

                    tableRow {
                        textView(R.string.capacity) {
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent)
                        tvCapacity = textView {
                            id = tv_capacity
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(12)
                    }

                    tableRow {
                        textView(R.string.location_team) {
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent)

                        tvLocation = textView {
                            id = tv_location
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(12)
                    }

                    tableRow {
                        textView(R.string.country) {
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent)
                        tvCountry = textView {
                            id = tv_country
                            textSize = 16f
                            textColor = ContextCompat.getColor(ctx, R.color.black)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        bottomMargin = dip(20)
                    }

                    textView(R.string.stadium_desc) {
                        textSize = 24f
                        textColor = ContextCompat.getColor(ctx, R.color.black)
                    }.lparams(width = wrapContent, height = wrapContent)

                    tvStadiumDesc = textView {
                        id = tv_stadium_desc
                        textSize = 14f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(12)
                        bottomMargin = dip(12)
                        topMargin = dip(12)
                    }

                    textView(R.string.team_desc) {
                        textSize = 24f
                        textColor = ContextCompat.getColor(ctx, R.color.black)
                    }.lparams(width = wrapContent, height = wrapContent)

                    tvTeamDesc = textView {
                        id = tv_team_desc
                        textSize = 14f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(12)
                        bottomMargin = dip(12)
                        topMargin = dip(12)
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                margin = dip(16)
            }
        }
    }
}

