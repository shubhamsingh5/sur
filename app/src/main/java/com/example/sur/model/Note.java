package com.example.sur.model;

public class Note {

    private String step;     //Z is for rest
    private String octave;
    private String alter;
    private String accidental;

    public Note() {

    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getOctave() {
        return octave;
    }

    public void setOctave(String octave) {
        this.octave = octave;
    }

    public String getAlter() {
        return alter;
    }

    public void setAlter(String alter) {
        this.alter = alter;
    }

    public String getAccidental() {
        return accidental;
    }

    public void setAccidental(String accidental) {
        this.accidental = accidental;
    }


}