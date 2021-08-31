package com.example.notes.framework.db

import androidx.room.*

@Dao
interface NoteDao {
    // Could be used for Add / update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNote(id: Long): NoteEntity?

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}