package com.example.a4_basic

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView


class NoteListFragment : Fragment() {
    private val model: Model by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_note_list, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        val recyclerViewAdapter = NotesAdapter(root.context, model)
        recyclerView.adapter = recyclerViewAdapter

        val switch: Switch = root.findViewById(R.id.importantToggle)
        switch.isChecked = model.showImportant
        switch.setOnCheckedChangeListener { _, _ ->
            model.toggleImportantNotes()
        }

        // buttons
        val randomButton = root.findViewById<Button>(R.id.random)
        randomButton.setOnClickListener {
            model.addRandomNote()

        }
        val clearButton = root.findViewById<Button>(R.id.clear)
        clearButton.setOnClickListener {
            model.clearNotes()
        }

        val searchText: EditText = root.findViewById(R.id.searchTextField)
        searchText.setText(model.searchTerm)
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                model.changeSearchTerm(s.toString())
            }
        })

        val addButton = root.findViewById<Button>(R.id.add)
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteList_to_addNote)
        }


        model.displayableNotes.observe(this) {
            recyclerViewAdapter.notes = it
            recyclerViewAdapter.notifyDataSetChanged()
            clearButton.isEnabled = it.size != 0
        }

        return root
    }
}