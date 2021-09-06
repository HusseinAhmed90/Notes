package com.example.notes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.core.usecase.*
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
        RemoveNote((repository)),
        GetWordsCount()
    )

    private val _saved = MutableLiveData<Boolean>()
    val saved :LiveData<Boolean>
        get() = _saved

    private val _currentNote = MutableLiveData<Note>()
    val currentNote :LiveData<Note>
        get() = _currentNote

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

    fun getNote(id: Long){
        coroutineScope.launch {
            val note = useCase.getNote(id)
            _currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note){
        coroutineScope.launch {
            useCase.removeNote(note)
            _saved.postValue(true)
        }
    }

}