package com.sanitcode.footballclubapp.ui.activity.detail.detailteam.player

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.R.id.*
import com.sanitcode.footballclubapp.model.player.PlayerModel
import com.sanitcode.footballclubapp.util.loadImage
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val context: Context?,
                    private val players: List<PlayerModel>,
                    private val listener: (position: Int) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.PlayerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        return PlayerListViewHolder(PlayerListUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        holder.bindItem(players[position], position)
    }

    override fun getItemCount(): Int = players.size

    inner class PlayerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPlayerName: TextView = view.find(R.id.tv_player_name)
        private val tvPlayerPosition: TextView = view.find(R.id.tv_player_position)
        private val imagePlayer: ImageView = view.find(R.id.iv_player)

        fun bindItem(item: PlayerModel, pos: Int) {
            tvPlayerName.text = item.strPlayer
            tvPlayerPosition.text = item.strPosition
            if (context != null) {
                loadImage(context, item.strCutout ?: "", imagePlayer)
            }
            itemView.onClick {
                listener(pos)
            }
        }

    }

    class PlayerListUI : AnkoComponent<ViewGroup> {
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

                        relativeLayout {

                            imageView {
                                id = iv_player
                            }.lparams {
                                width = dip(50)
                                height = dip(70)
                                margin = dip(10)
                                alignParentLeft()
                            }

                            verticalLayout {

                                textView {
                                    id = tv_player_name
                                    textSize = sp(6).toFloat()
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }

                                textView {
                                    textSize = sp(4).toFloat()
                                    id = R.id.tv_player_position
                                    textColor = ContextCompat.getColor(ctx, R.color.secondaryText)
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                centerVertically()
                                rightOf(iv_player)
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