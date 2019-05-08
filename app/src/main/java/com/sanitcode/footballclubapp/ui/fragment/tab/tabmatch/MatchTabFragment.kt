package com.sanitcode.footballclubapp.ui.fragment.tab.tabmatch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.ui.base.BaseFragment

class MatchTabFragment : BaseFragment() {

    private val titles = arrayOf<CharSequence>("Last", "Next")
    private val numberTabs = 2
    private lateinit var tabLayoutMatch: TabLayout
    private lateinit var viewPagerMatch: ViewPager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_match, container, false)
        tabLayoutMatch = view.findViewById(R.id.tab_layout)
        viewPagerMatch = view.findViewById(R.id.viewpager_match)

        val adapter = MatchTabAdapter(activity?.supportFragmentManager!!, titles, numberTabs)
        viewPagerMatch.adapter = adapter
        tabLayoutMatch.setupWithViewPager(viewPagerMatch)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuSearch = menu?.findItem(R.id.search_match)
        menuSearch?.isVisible = true
    }

}