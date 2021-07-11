package com.codexo.notes.adapters

import androidx.recyclerview.widget.DiffUtil
import com.codexo.notes.data.Note

class DiffUtil : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem

}