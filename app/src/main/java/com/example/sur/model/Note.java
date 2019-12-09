package com.example.sur.model;

public class Note {

    private String step;     //Z is for rest
    private int octave;
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

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
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

    public double getFrequency() {
        Frequency frequencies = Frequency.getInstance();
        double noteFreq = 0.0;
        if (this.step == null) {
            return noteFreq;
        }
        switch (this.step) {
            case "A": {
                noteFreq = frequencies.getScale().get(this.octave).a;
                break;
            }
            case "B": {
                noteFreq = frequencies.getScale().get(this.octave).b;
                break;
            }
            case "C": {
                noteFreq = frequencies.getScale().get(this.octave).c;
                break;
            }
            case "D": {
                noteFreq = frequencies.getScale().get(this.octave).d;
                break;
            }
            case "E": {
                noteFreq = frequencies.getScale().get(this.octave).e;
                break;
            }
            case "F": {
                noteFreq = frequencies.getScale().get(this.octave).f;
                break;
            }
            case "G": {
                noteFreq = frequencies.getScale().get(this.octave).g;
                break;
            }
        }
        return noteFreq;
    }

    public double getPosBucket(int samplingRate, int bufferSize) {
        double freq = getFrequency();
        return freq*2.0*bufferSize/samplingRate;
    }
}