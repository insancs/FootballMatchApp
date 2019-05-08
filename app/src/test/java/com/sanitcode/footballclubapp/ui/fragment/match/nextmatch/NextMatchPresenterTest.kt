package com.sanitcode.footballclubapp.ui.fragment.match.nextmatch

import android.content.Context
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.model.response.ScheduleResponse
import com.sanitcode.footballclubapp.ui.base.BasePresenterTest
import com.sanitcode.footballclubapp.ui.fragment.match.lastmatch.MatchView
import io.reactivex.Observable
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NextMatchPresenterTest : BasePresenterTest() {
    @Mock
    private
    lateinit var service: ApiSportDB

    @Mock
    private lateinit var context: Context

    @Mock
    private
    lateinit var view: MatchView

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, service, context)
    }

    @Test
    fun getMatch() {
        val list = mutableListOf<ItemModel>().apply {
            add(ItemModel())
        }
        val matchResponse = ScheduleResponse(list)
        Mockito.`when`(service.getNextMatch("4406")).thenReturn(
                Observable.just(matchResponse)
        )

        val inOrder = Mockito.inOrder(view)
        presenter.getMatch(true, "4406")
        inOrder.verify(view).showLoading(false)
        inOrder.verify(view).showMatchList(list)
    }

}