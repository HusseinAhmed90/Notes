package com.example.notes.framework.di

import com.example.core.repository.NoteRepository
import com.example.core.usecase.*
import com.example.notes.framework.NoteUseCases
import dagger.Module
import dagger.Provides


@Module
class UseCasesModule {

    @Provides
    fun provideUseCases(repository: NoteRepository) = NoteUseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordsCount()
    )

}