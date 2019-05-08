package com.sanitcode.footballclubapp.ui.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import com.sanitcode.footballclubapp.db.League
import com.sanitcode.footballclubapp.util.Util

open class BaseFragment : Fragment() {

    protected lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = context!!.getSharedPreferences(Util.SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    fun saveLeague(item: League) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(Util.ID_LEAGUE, item.idLeague)
        editor.putString(Util.STR_LEAGUE, item.strLeague)
        editor.apply()
    }

    fun getIdLeague(): String = preferences.getString(Util.ID_LEAGUE, "")
}