package com.sanitcode.footballclubapp.ui.fragment.tab.tabfavorite

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sanitcode.footballclubapp.ui.fragment.favorite.favoritematch.FavoriteMatchFragment
import com.sanitcode.footballclubapp.ui.fragment.favorite.favoriteteam.FavoriteTeamFragment

class FavoriteTabAdapter(fm: FragmentManager, private var titleFavorite: Array<CharSequence>, private var numberTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titleFavorite[position]
    }

    override fun getCount(): Int {
        return numberTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
        //Posisi 0 Tablayout FavoriteMatchFragment
            0 -> return FavoriteMatchFragment()
        //Posisi 1 Tablayout FavoriteTeamFragment
            1 -> return FavoriteTeamFragment()
        }
        return FavoriteMatchFragment()
    }
}