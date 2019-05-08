package com.sanitcode.footballclubapp.ui.fragment.match.nextmatch

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.util.formatDate
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class NextMatchAdapter(private val matches: List<ItemModel>,
                       private val listener: (position: Int) -> Unit,
                       private val listenerBell: (position: Int) -> Unit)
    : RecyclerView.Adapter<NextMatchAdapter.NextMatchViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        return NextMatchViewHolder(NextMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItem(matches[position], position)
    }

    override fun getItemCount(): Int = matches.size


    inner class NextMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate: TextView = view.find(tv_date_next)
        private val tvTeamHome: TextView = view.find(tv_home_next)
        private val tvTeamAway: TextView = view.find(tv_away_next)
        private val llContent: LinearLayout = view.find(content_next)
        private val ivClickBell: ImageView = view.find(add_calender)

        fun bindItem(item: ItemModel, pos: Int) {
            tvTeamHome.text = item.strHomeTeam
            tvTeamAway.text = item.strAwayTeam
            tvDate.text = formatDate(item.dateEvent ?: "")
            llContent.onClick { listener(pos) }
            ivClickBell.onClick { listenerBell(pos) }
        }

    }

    class NextMatchUI : AnkoComponent<ViewGroup> {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
                            backgroundDrawable = ctx.getDrawable(R.drawable.border_bottom)

                            textView {
                                textSize = sp(6).toFloat()
                                id = tv_date_next
                                textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            linearLayout {
                                id = content_next
                                textView {
                                    textSize = sp(6).toFloat()
                                    id = tv_home_next
                                    textColor = ContextCompat.getColor(ctx, R.color.black)
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    weight = 1F
                                }

                                textView {
                                    textSize = sp(8).toFloat()
                                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimaryDark)
                                    text = ctx.getString(R.string.versus)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    weight = 1F
                                }

                                textView {
                                    textSize = sp(6).toFloat()
                                    id = tv_away_next
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER
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
                            }

                            imageView {
                                id = add_calender
                                backgroundDrawable = ctx.getDrawable(R.drawable.ic_event_available)
                            }.lparams {
                                width = dip(20)
                                height = dip(20)
                                margin = dip(10)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }
                }
            }
        }
    }
}