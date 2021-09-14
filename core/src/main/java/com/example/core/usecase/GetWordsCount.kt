package com.example.core.usecase

import com.example.core.data.Note

class GetWordsCount {

    operator fun invoke(note: Note) = getCount(note.title + note.content)

    private fun getCount(string: String) = string.split(" ", "\n")
        .filter {
            it.contains(Regex(".*[a-zA-Z].*"))
        }.size

}