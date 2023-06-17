package com.example.marvellibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvellibrary.comicsToString
import com.example.marvellibrary.model.CharacterResult
import com.example.marvellibrary.model.db.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val appId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
){
    companion object {
        fun fromCharacter(character: CharacterResult) =
            DbCharacter(
                id = 0,
                appId = character.id,
                name = character.name,
                thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
                comics = character.comics?.items?.mapNotNull { it.name }?.comicsToString()
                    ?: "no comics",
                description = character.description
            )
    }
}