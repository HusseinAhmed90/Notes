package com.example.notes.framework.di

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule (val application: Application){

    @Provides
    fun provideApp() = application

}