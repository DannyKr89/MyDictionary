package ru.dk.mydictionary.utils


import com.example.api.retrofit.model.WordDTO
import com.example.history.room.HistoryWord
import ru.dk.mydictionary.data.model.Word

fun convertHistoryToWord(historyWord: HistoryWord): Word {
    return Word(
        word = historyWord.word,
        imageUrl = historyWord.imageUrl,
        transcription = historyWord.transcription,
        translation = historyWord.translation
    )
}

fun convertDTOToModel(wordDTO: WordDTO): Word {
    return Word(
        word = wordDTO.text,
        translation = wordDTO.meanings.first().translation.text,
        transcription = wordDTO.meanings.first().transcription,
        imageUrl = wordDTO.meanings.first().imageUrl
    )
}