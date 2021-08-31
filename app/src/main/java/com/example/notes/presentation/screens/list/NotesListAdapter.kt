package com.example.notes.presentation.screens.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.notes.databinding.ListItemNoteBinding

class NotesListAdapter(val clickListener: NotesListClickListener) :
    ListAdapter<Note, NotesListAdapter.ViewHolder>(NotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(private val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(note: Note, clickListener: NotesListClickListener) {
            binding.note = note
            // can be slightly faster to size the views
            binding.executePendingBindings()
            binding.listener = clickListener
//            binding.tvTitle.text = note.title
//            binding.tvContent.text = note.content
//            val sdf = SimpleDateFormat("MMMM dd, HH:mm:ss")
//            val resultDate = Date(note.updateTime)
//            binding.tvDate.text = "Last update: ${sdf.format(resultDate)}"
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}




