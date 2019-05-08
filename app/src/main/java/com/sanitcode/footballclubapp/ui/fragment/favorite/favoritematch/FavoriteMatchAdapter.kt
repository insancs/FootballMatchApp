package com.sanitcode.footballclubapp.ui.fragment.favorite.favoritematch

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.db.FavoriteMatch
import com.sanitcode.footballclubapp.ui.activity.detail.detailmatch.DetailMatchActivity
import com.sanitcode.footballclubapp.util.formatDate
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteMatchAdapter (private val context: Context?, private val favoriteMatches: List<FavoriteMatch>) :
        RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchViewHolder {
        return FavoriteMatchViewHolder(FavoriteMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favoriteMatches.size

    override fun onBindViewHolder(holder: FavoriteMatchViewHolder, position: Int) {
        holder.bindItem(favoriteMatches[position])
        holder.itemView.setOnClickListener {
            context!!.startActivity<DetailMatchActivity>("id" to favoriteMatches[position].eventId,
                    "eventTime" to "local",
                    "home" to favoriteMatches[position].homeId,
                    "away" to favoriteMatches[position].awayId)
        }
    }

    class FavoriteMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate: TextView = view.find(R.id.tv_date_fav_match)
        private val tvHomeTeam: TextView = view.find(R.id.tv_home_fav_match)
        private val tvAwayTeam: TextView = view.find(R.id.tv_away_fav_match)
        private val tvHomeScore: TextView = view.find(R.id.tv_home_score_fav)
        private val tvAwayScore: TextView = view.find(R.id.tv_away_score_fav)

        fun bindItem(item: FavoriteMatch) {
            tvHomeTeam.text = item.homeName
            tvAwayTeam.text = item.awayName
            if (item.eventMatch == "next") {
                tvHomeScore.visibility = View.INVISIBLE
                tvAwayScore.visibility = View.INVISIBLE
            } else {
                tvHomeScore.text = "${item.homeScore}"
                tvAwayScore.text = "${item.awayScore}"
            }
            tvDate.text = formatDate(item.eventDate ?: "")
        }

    }

    class FavoriteMatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                frameLayout {
                    lparams(matchParent, wrapContent)

                    cardView {
                        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                            topMargin = dip(5)
                            bottomMargin = dip(5)
                        }
                        background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                        radius = dip(8).toFloat()

                        verticalLayout {
                            padding = dip(8)

                            textView {
                                textSize = sp(6).toFloat()
                                id = R.id.tv_date_fav_match
                                textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            linearLayout {

                                textView {
                                    textSize = sp(6).toFloat()
                                    id = R.id.tv_home_fav_match
                                    textColor = ContextCompat.getColor(ctx, R.color.black)
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    weight = 1F
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER_HORIZONTAL

                                    textView {
                                        textSize = sp(8).toFloat()
                                        id = R.id.tv_home_score_fav
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColor = ContextCompat.getColor(ctx, R.color.black)
                                        gravity = Gravity.CENTER_HORIZONTAL
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                    }

                                    textView {
                                        textSize = sp(6).toFloat()
                                        text = ctx.getString(R.string.versus)
                                        textColor = ContextCompat.getColor(ctx, R.color.black)
                                        gravity = Gravity.CENTER_HORIZONTAL
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        leftMargin = dip(15)
                                        rightMargin = dip(15)
                                    }

                                    textView {
                                        textSize = sp(8).toFloat()
                                        id = R.id.tv_away_score_fav
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColor = ContextCompat.getColor(ctx, R.color.black)
                                        gravity = Gravity.CENTER_HORIZONTAL
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                    }

                                }.lparams {
                                    width = matchParent
                                    height = wrapContent

                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    weight = 1F
                                }

                                textView {
                                    textSize = sp(6).toFloat()
                                    id = R.id.tv_away_fav_match
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textColor = ContextCompat.getColor(ctx, R.color.black)
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    weight = 1F
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(10)
                                bottomMargin = dip(10)
                            }
                        }
                    }
                }
            }
        }
    }
}