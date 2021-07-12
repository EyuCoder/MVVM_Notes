package com.codexo.notes.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codexo.notes.R
import com.codexo.notes.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val viewModel: NotesViewModel by viewModels()
    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = _binding
    private val notesAdapter = NotesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNotesBinding.bind(view)

        binding?.apply {
            rvNotes.apply {
                adapter = notesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.allNotes.observe(viewLifecycleOwner) {
            notesAdapter.submitList(it)
        }

        fab_add.setOnClickListener { findNavController().navigate(R.id.action_notesFragment_to_addFragment) }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_notes, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                //viewModel.searchQuery = searchQuery
                searchNote(searchQuery)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_title -> true
            R.id.action_sort_by_date_created -> true
            R.id.action_sort_by_date_modified -> true
            R.id.action_pin_favorites -> {
                item.isChecked = !item.isChecked
                true
            }
            R.id.action_show_archived -> {
                item.isChecked = !item.isChecked
                true
            }
            R.id.action_delete_all_notes -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchNote(query: String) {
        val searchQuery = "%${query}%"
        viewModel.searchNote(searchQuery).observe(this, { list ->
            list?.let {
                notesAdapter.submitList(it)
            }
        })
    }
}