package com.example.a4_basic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


class AddEditNoteFragment : Fragment() {
    private val model: Model by activityViewModels()
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_edit_note, container, false)

        if (arguments != null && arguments!!.containsKey("noteId")) {
            val noteLabel = root.findViewById<TextView>(R.id.add_edit_note_label)
            val noteTitle = root.findViewById<TextView>(R.id.add_edit_note_title)
            val noteBody = root.findViewById<TextView>(R.id.add_edit_note_body)
            val importantSwitch = root.findViewById<Switch>(R.id.add_edit_note_switch)

            note = model.retrieveNote(arguments!!["noteId"] as Int)
            noteLabel.text = "Edit Note #" + note?.id.toString()
            noteTitle.text = note?.title.toString()
            noteBody.text = note?.body.toString()
            importantSwitch.isChecked = note?.important == true
        }

        val saveButton = root.findViewById<Button>(R.id.edit_button)
        saveButton.setOnClickListener {
            val noteTitleText: EditText = root.findViewById(R.id.add_edit_note_title)
            val noteBodyText: EditText = root.findViewById(R.id.add_edit_note_body)
            val noteImportantSwitch: Switch = root.findViewById(R.id.add_edit_note_switch)
            model.saveNote(
                Note(
                    note!!.id,
                    noteTitleText.text.toString(),
                    noteBodyText.text.toString(),
                    noteImportantSwitch.isChecked
                )
            )
            findNavController().navigate(R.id.action_addNote_to_noteList)
        }

        return root
    }
}