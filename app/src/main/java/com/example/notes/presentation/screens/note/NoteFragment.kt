package com.example.notes.presentation.screens.note

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.core.data.Note
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.framework.viewmodel.NoteViewModel

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var note = Note("", "", 0L, 0L)

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

        binding.fabSave.setOnClickListener {
            saveButtonAction()
        }

        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                viewModel.navigationEnd()
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

    private fun hideKeyBoard(){
        val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0);
    }
}