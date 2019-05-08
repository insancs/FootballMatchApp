package com.sanitcode.footballclubapp.ui.fragment.team

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.model.team.TeamModel
import com.sanitcode.footballclubapp.util.loadImage
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter(private val context: Context?,
                  private val teams: MutableList<TeamModel>,
                  private val listener: (position: Int) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {
        return TeamListViewHolder(TeamListUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.bindItem(teams[position], position)
    }

    override fun getItemCount(): Int = teams.size


    inner class TeamListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTeam: TextView = view.find(tv_name_teams)
        private val tvDesc: TextView = view.find(tv_desc_team)
        private val ivTeam: ImageView = view.find(R.id.iv_team)

        fun bindItem(item: TeamModel, pos: Int) {
            tvTeam.text = item.strTeam
            tvDesc.text = item.strDescriptionEN
            if (context != null) {
                loadImage(context, item.strTeamBadge ?: "", ivTeam)
            }
            itemView.onClick {
                listener(pos)
            }
        }
    }

    class TeamListUI : AnkoComponent<ViewGroup> {
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

                        relativeLayout {

                            imageView {
                                id = iv_team
                            }.lparams {
                                width = dip(70)
                                height = dip(70)
                                margin = dip(10)
                                alignParentLeft()
                            }

                            verticalLayout {

                                textView {
                                    id = tv_name_teams
                                    textSize = sp(6).toFloat()
                                    textColor = ContextCompat.getColor(ctx, R.color.black)
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }

                                textView {
                                    textSize = sp(4).toFloat()
                                    id = R.id.tv_desc_team
                                    maxLines = 3
                                    minLines = 2
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                rightOf(iv_team)
                            }

                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }
                    }
                }
            }
        }
    }
}