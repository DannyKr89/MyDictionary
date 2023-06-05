package ru.dk.mydictionary.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("select * from HistoryWord")
    suspend fun getAll(): List<HistoryWord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: HistoryWord)

    @Delete
    suspend fun delete(word: HistoryWord)
}