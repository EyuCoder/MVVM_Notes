package com.codexo.notes.ui.add

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.codexo.notes.R
import com.codexo.notes.data.Note
import com.codexo.notes.databinding.FragmentDetailBinding
import com.codexo.notes.ui.SharedViewModel
import com.codexo.notes.utils.HideKeyboard.Companion.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import java.util.*

class AddFragment : Fragment(R.layout.fragment_detail) {
    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        binding!!.apply {
            tvNoteDate.isVisible = false
            background.setBackgroundColor(-1)
            colorSlider.setListener { _, color ->
                background.setBackgroundColor(color)
            }
            fabStt.setOnClickListener { openSTTActivity() }
        }
        setHasOptionsMenu(true)
    }

    private fun openSTTActivity() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            .putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

        try {
            resultLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Snackbar.make(requireView(), "Your device does not support STT.", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            data?.let {
                binding!!.etAddNote.text = binding?.etAddNote?.text?.append(it[0])
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> addNewNote()
            android.R.id.home -> hideKeyboard()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewNote() {
        val title = binding!!.etAddTitle.text.toString()
        val note = binding!!.etAddNote.text.toString()
        val color = binding!!.colorSlider.selectedColor
        Log.d("XO", "$color")
        if (title.isEmpty() && note.isEmpty()) {
            val snackbar = Snackbar.make(
                requireView(),
                "Please fill out title or description of the note",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        } else {
            val newNote = Note(title = title, note = note, bgColor = color)
            viewModel.insertNote(newNote)
            val snackbar = Snackbar.make(requireView(), "'$title' saved!", Snackbar.LENGTH_LONG)
            snackbar.show()
            findNavController().navigate(R.id.action_addFragment_to_notesFragment)
        }
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}