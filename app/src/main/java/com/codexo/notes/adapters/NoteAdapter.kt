package com.codexo.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codexo.notes.data.Note
import com.codexo.notes.databinding.ItemNotesBinding

class NotesAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var NoteList = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = NoteList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    fun setData(notesData: List<Note>) {
        val notesDiffUtil = DiffUtil(NoteList, notesData)
        val notesDiffResult = DiffUtil.calculateDiff(notesDiffUtil)

        this.NoteList = notesData
        notesDiffResult.dispatchUpdatesTo(this)
    }

    inner class NoteViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.ivFavorite.setOnClickListener {
                listener.onFavoriteClicked(!note.favorite, note.id)
            }

            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onFavoriteClicked(markedFavorite: Boolean, id: Long)
    }
}