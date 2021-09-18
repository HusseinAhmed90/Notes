package com.example.notes.framework.di

import com.example.notes.framework.viewmodel.ListViewModel
import com.example.notes.framework.viewmodel.NoteViewModel
import dagger.Component


@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)

}