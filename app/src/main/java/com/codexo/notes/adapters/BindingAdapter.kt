package com.codexo.notes.adapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.codexo.notes.R
import com.codexo.notes.data.Note
import com.codexo.notes.ui.notes.NotesFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {
    companion object {
        @BindingAdapter("android:nav_to_add")
        @JvmStatic
        fun navToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_notesFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:empty_db")
        @JvmStatic
        fun emptyDb(view: View, emptyDb: MutableLiveData<Boolean>) {
            view.visibility = if (emptyDb.value == true) View.VISIBLE else View.INVISIBLE
        }

        @BindingAdapter("android:is_favorite")
        @JvmStatic
        fun isFavorite(img: ImageView, marked: Boolean) {
            if (marked) {
                img.setImageResource(R.drawable.ic_favorite_on)
            } else img.setImageResource(R.drawable.ic_favorite_off)
        }

        @BindingAdapter("android:send_notes_to_edit")
        @JvmStatic
        fun sendNotesToEdit(view: ConstraintLayout, currentItem: Note) {
            view.setOnClickListener {
                val action = NotesFragmentDirections.actionNotesFragmentToEditFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}