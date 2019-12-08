package com.example.sur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sur.model.Note;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayMusic extends AppCompatActivity {

    private Button startPlaying;
    private List<Note> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        startPlaying = findViewById(R.id.start_playing);
        Intent intent = getIntent();
        final String uriPath = intent.getStringExtra("uri");
        Log.d("uri path", uriPath);
        startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parse(uriPath);
            }
        });
    }

    private void parse(String path) {
        Log.d("TAG ",  "running");
        InputStream is = null;
        try {

            is = this.getContentResolver().openInputStream(Uri.parse(path));
            XMLPullParserHandler parser = new XMLPullParserHandler();
            notes = parser.parse(is);
            for (Note n : notes) {
                Log.d("NOTE: ", n.getStep() + n.getOctave());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
