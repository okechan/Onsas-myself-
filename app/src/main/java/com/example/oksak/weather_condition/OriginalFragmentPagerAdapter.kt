package com.example.oksak.weather_condition

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class OriginalFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>("カレンダー", "天気","体調管理","設定")

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return Main1Fragment()
            1 -> return Main2Fragment()
            2 -> return Main3Fragment()
            3 -> return Main4Fragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}