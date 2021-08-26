package com.codexo.notes.utils

const val THEME_PREF_KEY = "themePref"
const val VIEW_PREF_KEY = "viewPref"


enum class SortBy(val colName: String) {
    ID("id"),
    TITLE("title"),
    LAST_UPDATED_AT("last_updated_at"),
    CREATED_AT("created_at")
}