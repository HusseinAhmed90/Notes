package com.example.notes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.core.usecase.AddNote
import com.example.core.usecase.GetAllNotes
import com.example.core.usecase.GetNote
import com.example.core.usecase.RemoveNote
import com.example.notes.framework.NoteUseCases
import com.example.notes.framework.RoomNoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val useCase = NoteUseCases(
        AddNote((repository)),
        GetAllNotes((repository)),
        GetNote((repository)),
        RemoveNote((repository))
    )

    private val _saved = MutableLiveData<Boolean>()
    val saved :LiveData<Boolean> = _saved

    private val _currentNote = MutableLiveData<Note>()
    val currentNote :LiveData<Note> = _currentNote

    init {
        _saved.value = false
    }

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCase.addNote(note)
            _saved.postValue(true)
        }
    }

    fun navigationEnd() {
        _saved.value = false
    }

}