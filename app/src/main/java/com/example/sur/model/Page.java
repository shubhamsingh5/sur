package com.example.sur.model;

import java.util.ArrayList;

public class Page {
    private ArrayList<Note> notes;

    public Page() {
        notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

}
