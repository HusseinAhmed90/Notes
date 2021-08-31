package com.example.notes.framework

import android.content.Context
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource
import com.example.notes.framework.db.DatabaseService
import com.example.notes.framework.db.NoteEntity

class RoomNoteDataSource (context: Context): NoteDataSource{

    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNote(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNote(id)?.toNote()

    override suspend fun getAll() =
        noteDao.getAllNotes().map{
            it.toNote()
        }

    override suspend fun remove(note: Note) = noteDao.deleteNote(NoteEntity.fromNote(note))

}