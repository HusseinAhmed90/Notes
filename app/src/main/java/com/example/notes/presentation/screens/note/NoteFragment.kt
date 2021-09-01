package com.example.notes.presentation.screens.note

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.core.data.Note
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.framework.viewmodel.NoteViewModel

class NoteFragment : Fragment() {

    private var noteId: Long? = null
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var note = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        noteId?.let {
            viewModel.getNote(it)
        }

        viewModel.currentNote.observe(viewLifecycleOwner, Observer {
            it.apply {
                note = it
                binding.etTitle.setText(it.title)
                binding.etContent.setText(it.content)
            }
        })

        binding.fabSave.setOnClickListener {
            saveButtonAction()
        }

        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                viewModel.navigationEnd()
                noteId = null
            }
        })
    }

    private fun saveButtonAction() {
        if (binding.etTitle.text.toString().isNotEmpty() ||
            binding.etTitle.text.toString().isNotEmpty()
        ) {
            note.title = binding.etTitle.text.toString()
            note.content = binding.etContent.text.toString()
            val timeNow = System.currentTimeMillis()
            note.updateTime = timeNow
            if (note.creationTime == 0L) {
                note.creationTime = timeNow
            }
            viewModel.saveNote(note)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun hideKeyBoard() {
        val imm: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemDelete -> {
                noteId?.let{
                    AlertDialog.Builder(context)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure, that you want to delete this note?")
                        .setPositiveButton("Yes") {text, listener -> viewModel.deleteNote(note)}
                        .setNegativeButton("Cancel") {text, listener ->}
                        .create()
                        .show()
                }
            }
        }
        return true
    }

}