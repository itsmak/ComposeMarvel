package com.example.marvellibrary

import android.content.Context
import androidx.room.Room
import com.example.marvellibrary.model.Connectivity.ConnectivityMonitor
import com.example.marvellibrary.model.api.ApiService
import com.example.marvellibrary.model.api.MarvelApiRepo
import com.example.marvellibrary.model.db.*
import com.example.marvellibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun providesCharacterDao(collectionDb: CollectionDb) =
        collectionDb.characterDao()

    @Provides
    fun providesNoteDao(collectionDb: CollectionDb) =
        collectionDb.noteDao()

    @Provides
    fun providesDbRepoImpl(characterDao: CharacterDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionDbRepoIml(characterDao, noteDao)

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)

}