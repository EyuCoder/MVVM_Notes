package com.codexo.notes

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.codexo.notes.utils.THEME_PREF_KEY
import com.codexo.notes.utils.ThemeUtil

class JottyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = prefs.getString(THEME_PREF_KEY, ThemeUtil.DEFAULT_MODE)
        ThemeUtil.applyTheme(themePref!!)
    }
}