package com.sanitcode.footballclubapp.ui.activity.detail.detailteam

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sanitcode.footballclubapp.ui.activity.detail.detailteam.overview.OverviewTeamFragment
import com.sanitcode.footballclubapp.ui.activity.detail.detailteam.player.PlayerFragment

class ViewPagerAdaper (fm: FragmentManager, private var Titles: Array<CharSequence>, private var NumberTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return Titles[position]
    }

    override fun getCount(): Int {
        return NumberTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return OverviewTeamFragment()
            1 -> return PlayerFragment()
        }
        return OverviewTeamFragment()
    }
}