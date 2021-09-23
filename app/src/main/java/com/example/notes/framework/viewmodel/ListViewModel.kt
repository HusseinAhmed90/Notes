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

class ListViewModel(application: Application) : AndroidViewModel(application){

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    
    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val useCases = NoteUseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordsCount()
    )

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun getNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()
            noteList.forEach {
                it.wordsCount = "${useCases.wordsCount.invoke(it)} word/s"
            }
            _notes.postValue(noteList)
        }
    }

}