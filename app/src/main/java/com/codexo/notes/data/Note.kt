package com.codexo.notes.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,
    @ColumnInfo(name = "last_updated_at")
    var lastUpdatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "bg_color")
    val bgColor: Int,
    @ColumnInfo(name = "archived")
    val archived: Boolean = false,

    ) : Parcelable {
    val createdAtFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)

    val lastUpdatedAtFormatted: String
        get() = DateFormat.getDateTimeInstance().format(lastUpdatedAt)
}
