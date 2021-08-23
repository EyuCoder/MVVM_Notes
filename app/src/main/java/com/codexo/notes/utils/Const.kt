package com.codexo.notes.utils

const val REQUEST_CODE_STT = 1
const val THEME_PREF_KEY = "themePref"

enum class SortBy(val colName: String) {
    ID("id"),
    TITLE("title"),
    LAST_UPDATED_AT("last_updated_at"),
    CREATED_AT("created_at")
}