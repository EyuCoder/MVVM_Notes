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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codexo.notes.R
import com.codexo.notes.adapters.NotesAdapter
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
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }
        }

        viewModel.allNotes.observe(viewLifecycleOwner) {
            notesAdapter.setData(it)
        }

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
        when (item.itemId) {
            R.id.action_sort_by_title -> {
                viewModel.sortBy(sortBy[0]).observe(this, { notesAdapter.setData(it) })
            }
            R.id.action_sort_by_date_created -> {
                viewModel.sortBy("created_at").observe(this, { notesAdapter.setData(it) })
            }
            R.id.action_sort_by_date_modified -> {
                viewModel.sortBy(sortBy[2]).observe(this, { notesAdapter.setData(it) })
            }
            R.id.action_about -> {
                findNavController().navigate(R.id.action_notesFragment_to_aboutFragment)
            }
            R.id.action_delete_all_notes -> {
                deleteAllDialog()
                viewModel.deleteAllNotes()
            }
            else -> true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllDialog() {
        TODO("Not yet implemented")
    }

    private fun searchNote(query: String) {
        val searchQuery = "%${query}%"
        viewModel.searchNote(searchQuery).observe(this, { list ->
            list?.let {
                notesAdapter.setData(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val sortBy = listOf("title", "created_at", "last_updated_at", "favorite")
    }
}