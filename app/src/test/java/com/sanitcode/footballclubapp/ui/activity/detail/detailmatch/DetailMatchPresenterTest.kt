package com.sanitcode.footballclubapp.ui.activity.detail.detailmatch

import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.match.MatchModel
import com.sanitcode.footballclubapp.model.match.MatchResponse
import com.sanitcode.footballclubapp.model.team.TeamModel
import com.sanitcode.footballclubapp.model.team.TeamResponse
import com.sanitcode.footballclubapp.ui.base.BasePresenterTest
import io.reactivex.Observable
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMatchPresenterTest : BasePresenterTest() {
    @Mock
    private
    lateinit var service: ApiSportDB

    @Mock
    private
    lateinit var view: DetailMatchView

    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view, service)
    }

    @Test
    fun getDetailMatch() {
        val id = "576504"
        val list = mutableListOf<MatchModel>().apply {
            add(MatchModel())
        }
        val matchResponse = MatchResponse(list)
        Mockito.`when`(service.getMatchDetail(id)).thenReturn(
                Observable.just(matchResponse)
        )

        val inOrder = Mockito.inOrder(view)
        presenter.getDetailMatch(id)
        inOrder.verify(view).showLoading(false)
        inOrder.verify(view).showDetailMatch(list[0])
    }

    @Test
    fun getBadge() {
        val id = "133637"
        val home = 1
        val list = mutableListOf<TeamModel>().apply {
            add(TeamModel())
        }
        val teamResponse = TeamResponse(list)
        Mockito.`when`(service.getDetailTeam(id)).thenReturn(
                Observable.just(teamResponse)
        )

        val inOrder = Mockito.inOrder(view)
        presenter.getBadge(id, home)
        inOrder.verify(view).showBadgeImageHome(list[0].strTeamBadge ?: "")
    }

}