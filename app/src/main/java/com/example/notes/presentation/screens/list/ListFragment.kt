package com.example.notes.presentation.screens.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.FragmentListBinding
import com.example.notes.framework.viewmodel.ListViewModel

class ListFragment : Fragment(), NotesListClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notesAdapter = NotesListAdapter(this)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }
        binding.fabAddNote.setOnClickListener {
            goToNoteDetailsFragment(0L)
        }
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.notes.observe(viewLifecycleOwner, Observer { notesList ->
            binding.progressBar.visibility = View.GONE
            binding.rvNotes.visibility = View.VISIBLE
            notesAdapter.submitList(notesList.sortedByDescending {
                it.updateTime
            })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun onItemClicked(id: Long) {
        goToNoteDetailsFragment(id)
    }

    private fun goToNoteDetailsFragment(id: Long = 0L) {
        val action = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        findNavController().navigate(action)
    }

}