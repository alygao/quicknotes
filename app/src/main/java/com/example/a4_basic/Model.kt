package com.example.a4_basic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class Model: ViewModel() {
    private val notes: SortedMap<Int, Note> = TreeMap()
    var showImportant: Boolean = false
    var searchTerm: String = ""
    private var selectedNoteCard = -1
    private var nextNoteId: Int = 0
    val displayableNotes : MutableLiveData<ArrayList<Note>> = MutableLiveData<ArrayList<Note>>()
    val status : MutableLiveData<String> = MutableLiveData<String>()
    val actionTaken: MutableLiveData<String> = MutableLiveData<String>()


    fun saveNote(note: Note) {
        if (note.id == -1) {
            note.id = nextNoteId
            nextNoteId += 1
            actionTaken.value = "Added Note #${note.id}"
        } else {
            actionTaken.value = "Edited Note #${note.id}"
        }
        notes[note.id] = note
        updateDisplayableNotes()
    }

    private fun generateRandomNote() : Note{
        return Note(Util.getRandomTitle(), Util.getRandomBody(), Util.getRandomImportance())
    }

    fun addRandomNote() {
        val note = generateRandomNote()
        note.id = nextNoteId++
        notes[note.id] = note
        updateDisplayableNotes()
        actionTaken.value = "Added Note #${note.id}"
    }

    fun retrieveNote(noteId: Int) : Note? {
        return notes[noteId]
    }

    fun deleteNote(noteId: Int) {
        if (noteId >= 0) {
            notes.remove(noteId)
            if (selectedNoteCard == noteId) selectedNoteCard = -1
            updateDisplayableNotes()
            actionTaken.value = "Deleted Note #${noteId}"
        }
    }

    fun clearNotes() {
        if (!showImportant && searchTerm == "") {
            actionTaken.value = "Cleared ${notes.size} Notes"
            notes.clear()
            selectedNoteCard = -1
        } else {
            val toBeDeleted = ArrayList<Int>()
            for ((id, note) in notes) {
                if (showImportant && !note.important) {
                    continue
                }
                if (searchTermInNote(note)) {
                    if (id == selectedNoteCard) selectedNoteCard = -1
                    toBeDeleted.add(id)
                }
            }
            for (id in toBeDeleted) {
                notes.remove(id)
            }
            actionTaken.value = "Cleared ${toBeDeleted.size} Notes"
        }
        updateDisplayableNotes()
    }

    private fun searchTermInNote(note: Note) : Boolean {
        return note.title.uppercase().indexOf(searchTerm) >= 0 || note.body.uppercase().indexOf(searchTerm) >= 0
    }

    private fun updateDisplayableNotes() {
        val displayableNotesTemp: ArrayList<Note> = ArrayList()
        for (note in notes.values) {
            if (showImportant && !note.important) {
                continue
            }
            if (searchTermInNote(note)) {
                displayableNotesTemp.add(note)
            }
        }
        displayableNotes.value = displayableNotesTemp
        updateStatus()
    }

    fun toggleImportantNotes() {
        showImportant = !showImportant
        updateDisplayableNotes()
    }

    fun changeSearchTerm(term: String) {
        searchTerm = term.uppercase()
        updateDisplayableNotes()
    }

    private fun updateStatus() {
        status.value =
            if (!showImportant && searchTerm == "") {
                "(${notes.size} notes)"
            } else {
                "(matches ${displayableNotes.value?.size} of ${notes.size} notes)"
            }
    }
}
