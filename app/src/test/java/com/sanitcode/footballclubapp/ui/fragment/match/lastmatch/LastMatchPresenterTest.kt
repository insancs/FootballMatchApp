package com.sanitcode.footballclubapp.ui.fragment.match.lastmatch

import android.content.Context
import com.sanitcode.footballclubapp.api.ApiSportDB
import com.sanitcode.footballclubapp.model.ItemModel
import com.sanitcode.footballclubapp.model.response.ScheduleResponse
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
class LastMatchPresenterTest : BasePresenterTest() {
    @Mock
    private
    lateinit var service: ApiSportDB

    @Mock
    private
    lateinit var context: Context

    @Mock
    private
    lateinit var view: MatchView

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view, service, context)
    }

    @Test
    fun getMatch() {
        val list = mutableListOf<ItemModel>().apply {
            add(ItemModel())
        }
        val matchResponse = ScheduleResponse(list)
        Mockito.`when`(service.getLastMatch("4406")).thenReturn(
                Observable.just(matchResponse)
        )

        val inOrder = Mockito.inOrder(view)
        presenter.getMatch(true, "4406")
        inOrder.verify(view).showLoading(false)
        inOrder.verify(view).showMatchList(list)
    }
}