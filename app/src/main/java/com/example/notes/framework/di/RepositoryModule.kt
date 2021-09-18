package com.example.notes.framework.di

import android.app.Application
import com.example.core.repository.NoteRepository
import com.example.notes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideRepository(application: Application) = NoteRepository(RoomNoteDataSource(application))

}