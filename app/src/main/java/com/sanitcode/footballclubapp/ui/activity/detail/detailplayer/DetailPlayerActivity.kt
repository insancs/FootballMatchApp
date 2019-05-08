package com.sanitcode.footballclubapp.ui.activity.detail.detailplayer

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.ui.base.BaseActivity
import com.sanitcode.footballclubapp.util.Util
import com.sanitcode.footballclubapp.util.formatDate
import com.sanitcode.footballclubapp.util.loadImage
import kotlinx.android.synthetic.main.activity_detail_player.*
import kotlinx.android.synthetic.main.detail_player.*

class DetailPlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        init()
    }

    private fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_player_name.text = intent.getStringExtra(Util.NAME_PLAYER)
        tv_player_position.text = intent.getStringExtra(Util.POSITION_PLAYER)
        tv_team_name.text = intent.getStringExtra(Util.NAME_TEAM)
        val dateBorn = intent.getStringExtra(Util.DATE_BORN)
        tv_dateborn_player.text = formatDate(dateBorn)
        //tv_dateborn_player.text = intent.getStringExtra(Util.DATE_BORN)
        tv_datelocation_player.text = intent.getStringExtra(Util.DATE_LOCATION)
        tv_gender.text = intent.getStringExtra(Util.GENDER)
        tv_nationality.text = intent.getStringExtra(Util.NATIONALITY)
        tv_player_height.text = intent.getStringExtra(Util.HEIGHT_PLAYER)
        tv_player_weight.text = intent.getStringExtra(Util.WEIGHT_PLAYER)
        tv_loved.text = intent.getStringExtra(Util.LOVED)
        tv_date_signed.text = intent.getStringExtra(Util.DATE_SIGNED)
        tv_budget.text = intent.getStringExtra(Util.BUDGET)
        tv_player_income.text = intent.getStringExtra(Util.INCOME_PLAYER)
        tv_desc_player.text = intent.getStringExtra(Util.DESC_PLAYER)

        if (!isFinishing) {
            //Kondisi ketika COVER_PLAYER dan BADGE_PLAYER masih null
            if (!intent.getStringExtra(Util.COVER_PLAYER).isNullOrEmpty() && !intent.getStringExtra(Util.BADGE_PLAYER).isNullOrEmpty()) {
                loadImage(this, intent.getStringExtra(Util.COVER_PLAYER), iv_cover_player_detail)
                loadImage(this, intent.getStringExtra(Util.BADGE_PLAYER), iv_player_badge)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_OK)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
