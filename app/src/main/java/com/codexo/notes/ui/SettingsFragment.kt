package com.codexo.notes.ui

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.codexo.notes.R
import com.codexo.notes.utils.THEME_PREF_KEY
import com.codexo.notes.utils.ThemeUtil

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val themePreference: ListPreference? = findPreference(THEME_PREF_KEY)
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val themeOption: String = newValue as String
            ThemeUtil.applyTheme(themeOption)
            true
        }
    }
}