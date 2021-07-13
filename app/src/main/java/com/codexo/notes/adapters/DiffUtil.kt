package com.codexo.notes.adapters

import androidx.recyclerview.widget.DiffUtil
import com.codexo.notes.data.Note

class DiffUtil(private val oldList: List<Note>, private val newList: List<Note>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItemPosition == newItemPosition

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].note == newList[newItemPosition].note &&
                oldList[oldItemPosition].favorite == newList[newItemPosition].favorite
    }
}