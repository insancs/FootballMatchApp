package com.sanitcode.footballclubapp.ui.fragment.tab.tabmatch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sanitcode.footballclubapp.ui.fragment.match.nextmatch.NextMatchFragment
import com.sanitcode.footballclubapp.ui.fragment.match.lastmatch.LastMatchFragment

class MatchTabAdapter(fm: FragmentManager, private var titleMatch: Array<CharSequence>, private var numberTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titleMatch[position]
    }

    override fun getCount(): Int {
        return numberTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return LastMatchFragment()
            1 -> return NextMatchFragment()
        }
        return LastMatchFragment()
    }
}
