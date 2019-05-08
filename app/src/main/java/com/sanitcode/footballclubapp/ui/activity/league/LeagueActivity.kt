package com.sanitcode.footballclubapp.ui.activity.league

import android.os.Bundle
import com.sanitcode.footballclubapp.ui.activity.main.MainActivity
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.ui.base.BaseActivity
import org.jetbrains.anko.*

class LeagueActivity : BaseActivity(), LeagueView {

    private lateinit var presenter: LeaguePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        presenter = LeaguePresenter(this, ApiSportDB.instance, this)
        presenter.getLeagueDB()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showLeagueList() {
        startActivity<MainActivity>()
        finish()
    }
}
