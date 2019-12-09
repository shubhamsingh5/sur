package com.example.sur.model;

import java.util.ArrayList;

public class Frequency {
    private double A4 = 440;
    double a;
    private Octave octave;

    private static Frequency instance;

    private static ArrayList<Octave> scale;

    public Frequency() {
        scale = new ArrayList<>();
        setScale();
    }

    public static Frequency getInstance() {
        if (instance == null) {
            instance = new Frequency();
        }
        return instance;
    }

    private void setScale() {
        for (int i = -4; i < 4; i++) {
            a = A4 * Math.pow(2, i);
            octave = new Octave(a);
            scale.add(octave);
        }
    }

    public ArrayList<Octave> getScale() {
        return scale;
    }

    class Octave {
        double b, a, g, f, e, d, c;

        Octave(double a) {
            this.b = a * Math.pow(2, 0.16666667);
            // this.aSharp = a *  Math.pow(2, 1/12);
            this.a = a;
            // this.gSharp = a *  Math.pow(2, -1/12);
            this.g = a * Math.pow(2, -0.166666667);
            // this.fSharp = a *  Math.pow(2, -3/12);
            this.f = a * Math.pow(2, -0.333333356);
            this.e = a * Math.pow(2, -0.416666667);
            //  this.dSharp = a *  Math.pow(2, -6/12);
            this.d = a * Math.pow(2, -0.583333333);
            // this.cSharp = a *  Math.pow(2, -8/12);
            this.c = a * Math.pow(2, -0.75);
        }
    }
}