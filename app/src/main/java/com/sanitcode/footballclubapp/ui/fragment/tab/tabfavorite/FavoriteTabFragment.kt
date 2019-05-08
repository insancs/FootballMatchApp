package com.sanitcode.footballclubapp.ui.fragment.tab.tabfavorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanitcode.footballclubapp.R
import com.sanitcode.footballclubapp.ui.base.BaseFragment

class FavoriteTabFragment : BaseFragment() {

    private val titles = arrayOf<CharSequence>("Match", "Team")
    private val numberTabs = 2
    private lateinit var tabLayoutFav: TabLayout
    private lateinit var viewPagerFav: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_favorite, container, false)
        tabLayoutFav = view.findViewById(R.id.tab_layout_fav)
        viewPagerFav = view.findViewById(R.id.viewpager_fav)

        val adapter = FavoriteTabAdapter(activity?.supportFragmentManager!!, titles, numberTabs)
        viewPagerFav.adapter = adapter
        tabLayoutFav.setupWithViewPager(viewPagerFav)

        return view
    }
}