package com.sanitcode.footballclubapp.ui.fragment.match.lastmatch

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
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.util.formatDate
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class LastMatchAdapter(private val matches: List<ItemModel>,
                       private val listener: (position: Int) -> Unit)
    : RecyclerView.Adapter<LastMatchAdapter.PrevMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevMatchViewHolder {
        return PrevMatchViewHolder(PrevMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PrevMatchViewHolder, position: Int) {
        holder.bindItem(matches[position], position)
    }

    override fun getItemCount(): Int = matches.size


    inner class PrevMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate: TextView = view.find(R.id.tvdate_last)
        private val tvTeamHome: TextView = view.find(R.id.tv_home_last)
        private val tvTeamAway: TextView = view.find(R.id.tv_away_last)
        private val tvHomeSkor: TextView = view.find(R.id.tv_home_score_last)
        private val tvAwaySkor: TextView = view.find(R.id.tv_away_score_last)

        fun bindItem(item: ItemModel, pos: Int) {
            tvTeamHome.text = item.strHomeTeam
            tvTeamAway.text = item.strAwayTeam
            tvHomeSkor.text = item.intHomeScore
            tvAwaySkor.text = item.intAwayScore
            tvDate.text = formatDate(item.dateEvent ?: "")

            itemView.onClick {
                listener(pos)
            }
        }

    }

    class PrevMatchUI : AnkoComponent<ViewGroup> {
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
                                id = R.id.tvdate_last
                                textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            linearLayout {

                                textView {
                                    textSize = sp(6).toFloat()
                                    id = R.id.tv_home_last
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
                                        id = R.id.tv_home_score_last
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
                                        id = R.id.tv_away_score_last
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
                                    id = R.id.tv_away_last
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