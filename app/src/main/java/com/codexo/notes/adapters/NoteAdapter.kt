package com.codexo.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codexo.notes.data.Note
import com.codexo.notes.databinding.ItemNotesBinding

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.TodoViewHolder>() {

    var todoList = emptyList<Note>()

    class TodoViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNotesBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(notesData: List<Note>) {
        val notesDiffUtil = DiffUtil(todoList, notesData)
        val notesDiffResult = DiffUtil.calculateDiff(notesDiffUtil)

        this.todoList = notesData
        notesDiffResult.dispatchUpdatesTo(this)
    }
}