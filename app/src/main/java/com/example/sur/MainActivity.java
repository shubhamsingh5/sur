package com.example.sur;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG ",  "running");
        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.game);
        XMLPullParserHandler parser = new XMLPullParserHandler();
        notes = parser.parse(is);
        for (Note n : notes) {
            Log.d("NOTE: ", n.getStep() + n.getOctave());
        }
    }
}
