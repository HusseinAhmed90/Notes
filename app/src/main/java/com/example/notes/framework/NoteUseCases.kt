package com.example.notes.framework

import com.example.core.usecase.*

data class NoteUseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val wordsCount: GetWordsCount
)