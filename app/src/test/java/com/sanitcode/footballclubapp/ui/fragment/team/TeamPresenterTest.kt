package com.sanitcode.footballclubapp.ui.fragment.team

import android.content.Context
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.team.TeamModel
import com.sanitcode.footballclubapp.model.team.TeamResponse
import com.sanitcode.footballclubapp.ui.base.BasePresenterTest
import io.reactivex.Observable
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest: BasePresenterTest() {
    @Mock
    private lateinit var service: ApiSportDB

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var viewTeam: TeamView

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(viewTeam, service, context)
    }

    @Test
    fun getTeams() {
        val list = mutableListOf<TeamModel>().apply {
            add(TeamModel())
        }
        val matchResponse = TeamResponse(list)
        Mockito.`when`(service.getTeamAllLeague("4406")).thenReturn(
                Observable.just(matchResponse)
        )

        val inOrder = Mockito.inOrder(viewTeam)
        presenter.getTeams(true, "4406")
        inOrder.verify(viewTeam).showLoading(false)
        inOrder.verify(viewTeam).showTeamList(list)
    }

}