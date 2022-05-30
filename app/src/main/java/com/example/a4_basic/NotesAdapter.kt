package com.example.a4_basic
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val context: Context,
    private val model: Model
) : RecyclerView.Adapter<NotesAdapter.ItemViewHolder>() {
    var notes: List<Note> = listOf()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTitleTextView: TextView = view.findViewById(R.id.add_edit_note_title)
        val noteBodyTextView: TextView = view.findViewById(R.id.note_body)
        val cardView:ConstraintLayout = view.findViewById(R.id.note_card)
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]
        if (note.important) {
            holder.cardView.setBackgroundColor(context.resources.getColor(R.color.light_pink))
        } else {
            holder.cardView.setBackgroundColor(context.resources.getColor(R.color.white))
        }
        if (model.showImportant && note.important || !model.showImportant) {
            holder.noteTitleTextView.text = note.title
            holder.noteBodyTextView.text = note.body
            holder.deleteButton.setOnClickListener {
                model.deleteNote(note.id)
            }
            holder.cardView.setOnClickListener{ view ->
                val noteId = note.id
                val action = NoteListFragmentDirections.actionNoteListToSingleNote(noteId)
                view.findNavController().navigate(action)
            }

        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = notes.size
}