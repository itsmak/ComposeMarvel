package com.example.marvellibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvellibrary.model.CharacterResult
import com.example.marvellibrary.model.Note
import com.example.marvellibrary.model.db.CollectionDbRepo
import com.example.marvellibrary.model.db.DbCharacter
import com.example.marvellibrary.model.db.DbNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(private val repo: CollectionDbRepo): ViewModel(){

    val currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())
    val notes = MutableStateFlow<List<DbNote>>(listOf())

    init {
        getCollection()
        getNotes()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repo.getCharactersFromRepo().collect{
                collection.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId: Int?){
        characterId?.let {
            viewModelScope.launch {
                repo.getCharacterFromRepo(it).collect{
                    currentCharacter.value = it
                }
            }
        }
    }

    fun addCharacter(character: CharacterResult){
        viewModelScope.launch (Dispatchers.IO){
            repo.addCharacterToRepo(DbCharacter.fromCharacter(character))
        }
    }

    fun deleteCharacter(character: DbCharacter){
        viewModelScope.launch (Dispatchers.IO){
            repo.deleteAllNotes(character)
            repo.deleteCharacterFromRepo(character)
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            repo.getAllNotes().collect{
                notes.value = it
            }
        }
    }

    fun addNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNoteToRepo(DbNote.fromNote(note))
        }
    }

    fun deleteNote(note: DbNote){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNoteFromRepo(note)
        }
    }
}