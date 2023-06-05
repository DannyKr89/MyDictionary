package ru.dk.mydictionary.data

import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.model.Meaning
import ru.dk.mydictionary.data.model.Translation
import ru.dk.mydictionary.data.room.HistoryWord

fun convertHistoryToModel(historyWord: HistoryWord): DictionaryModel {
    return DictionaryModel(
        text = historyWord.word,
        meanings = listOf(
            Meaning(
                imageUrl = historyWord.imageUrl,
                soundUrl = null,
                transcription = historyWord.transcription,
                translation = Translation(historyWord.translation)
            )
        )
    )
}