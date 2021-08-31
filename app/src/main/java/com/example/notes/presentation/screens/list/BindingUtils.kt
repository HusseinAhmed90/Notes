package com.example.notes.presentation.screens.list

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.core.data.Note
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("lastUpdateTime")
fun TextView.setLastUpdate(note: Note?) {
    note?.let {
        val sdf = SimpleDateFormat("MMMM dd, HH:mm:ss")
        val resultDate = Date(note.updateTime)
        text = "Last update: ${sdf.format(resultDate)}"
    }
}