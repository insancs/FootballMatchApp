package com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam

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
import com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam.FavoriteTeamAdapter.TeamListViewHolder
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.db.FavoriteTeam
import com.sanitcode.footballclubapp.util.loadImage
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamAdapter(private val context: Context?,
                          private val favTeams: List<FavoriteTeam>,
                          private val listener: (position: Int) -> Unit) :
        RecyclerView.Adapter<TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {
        return TeamListViewHolder(FavoriteTeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favTeams.size

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.bindItem(favTeams[position], position)
    }

    inner class TeamListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Mendeklarasikan variabel
        private val tvTeam: TextView = view.find(R.id.tv_name_fav_team)
        private val tvTeamDesc: TextView = view.find(R.id.tv_desc_fav_eam)
        private val ivTeamBadge: ImageView = view.find(R.id.iv_fav_team)

        fun bindItem(item: FavoriteTeam, pos: Int) {
            tvTeam.text = item.teamName
            tvTeamDesc.text = item.descriptionEN
            if (context != null) {
                loadImage(context, item.teamBadge ?: "", ivTeamBadge)
            }
            itemView.onClick {
                listener(pos)
            }
        }
    }

    class FavoriteTeamUI : AnkoComponent<ViewGroup> {
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
                                id = R.id.iv_fav_team
                            }.lparams {
                                width = dip(70)
                                height = dip(70)
                                margin = dip(10)
                                alignParentLeft()
                            }

                            verticalLayout {

                                textView {
                                    id = R.id.tv_name_fav_team
                                    textSize = sp(6).toFloat()
                                    textColor = ContextCompat.getColor(ctx, R.color.black)
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }

                                textView {
                                    textSize = sp(4).toFloat()
                                    id = R.id.tv_desc_fav_eam
                                    textColor = ContextCompat.getColor(ctx, R.color.secondaryText)
                                    maxLines = 2
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
                                rightOf(R.id.iv_fav_team)

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