package com.codexo.notes.ui.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.codexo.notes.R
import com.codexo.notes.data.Note
import com.codexo.notes.databinding.FragmentDetailBinding
import com.codexo.notes.ui.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class EditFragment : Fragment(R.layout.fragment_detail) {
    private val args: EditFragmentArgs by navArgs()
    private val viewModel: SharedViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        binding?.apply {
            etAddTitle.setText(args.currentItem.title)
            etAddNote.setText(args.currentItem.note)
            tvNoteDate.text = args.currentItem.lastUpdatedAtFormatted
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_update -> updateNote()
            R.id.menu_delete -> deleteNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val title = binding?.etAddTitle?.text.toString()

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteItem(args.currentItem)
            val snackbar = Snackbar.make(requireView(), "'$title' deleted!", Snackbar.LENGTH_LONG)
            snackbar.show()
            findNavController().navigate(R.id.action_editFragment_to_notesFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '$title'?")
        builder.setMessage("Are you sure you want to Remove '$title'?")
        builder.create().show()
    }

    private fun updateNote() {
        val title = binding?.etAddTitle?.text.toString()
        val note = binding?.etAddNote?.text.toString()
        if (title.isEmpty() && note.isEmpty()) {
            val snackbar = Snackbar.make(
                requireView(),
                "Please fill out one of the fields.",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        } else {
            val newNote = Note(
                id = args.currentItem.id,
                title = title,
                note = note,
                lastUpdatedAt = System.currentTimeMillis()
            )
            viewModel.updateNote(newNote)
            val snackbar = Snackbar.make(requireView(), "'$title' updated!", Snackbar.LENGTH_LONG)
            snackbar.show()
            findNavController().navigate(R.id.action_editFragment_to_notesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}