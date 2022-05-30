package com.example.a4_basic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip


class SingleNoteFragment : Fragment() {
    private val model: Model by activityViewModels()
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_single_note, container, false)

        // receive arguments here
        if (arguments != null && arguments!!.containsKey("noteId")) {
            val noteIdTitle = root.findViewById<TextView>(R.id.add_edit_note_label)
            val noteTitle = root.findViewById<TextView>(R.id.add_edit_note_title)
            val noteBody = root.findViewById<TextView>(R.id.add_edit_note_body)
            val importantChip = root.findViewById<Chip>(R.id.important_chip)

            note = model.retrieveNote(arguments!!["noteId"] as Int)
            noteIdTitle.text = "Note #" + note?.id.toString()
            noteTitle.text = note?.title.toString()
            noteBody.text = note?.body.toString()
            importantChip.isVisible = note?.important == true
        }

        val editButton = root.findViewById<Button>(R.id.edit_button)
        editButton.setOnClickListener {
            val noteId = note?.id ?: -1
            val action = SingleNoteFragmentDirections.actionSingleNoteToAddEditNote(noteId)
            findNavController().navigate(action)
        }

        return root
    }
}