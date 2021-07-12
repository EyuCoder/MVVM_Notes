package com.codexo.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codexo.notes.data.Note
import com.codexo.notes.databinding.ItemNotesBinding

class NotesAdapter : ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class NotesViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvNotes.text = note.note
                ivFavorite.isVisible = note.favorite
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }
}