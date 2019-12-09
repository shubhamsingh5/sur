package com.example.sur;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sur.fft.RealDoubleFFT;
import com.example.sur.model.Frequency;
import com.example.sur.model.Note;
import com.example.sur.model.Page;
import com.example.sur.model.Score;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

public class PlayMusic extends AppCompatActivity {

    private Button startPlaying;
    private Score score;

    int frequency = 8000;
    int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RealDoubleFFT transformer;
    int blockSize = 256;

    RecordAudio recordTask;

    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        startPlaying = findViewById(R.id.start_playing);
        transformer = new RealDoubleFFT(blockSize);
        Intent intent = getIntent();
        final String uriPath = intent.getStringExtra("uri");
        Log.d("uri path", uriPath);
        startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (started) {
                    started = false;
                    recordTask.cancel(true);
                } else {
                    parse(uriPath);
                    for (Page p : score.getPages()) {
                        Log.d("new", "page");
                        for (Note n : p.getNotes()) {
                            Log.d("new", n.getStep() + n.getOctave() +  " " + n.getFrequency());
                        }
                    }
                    recordTask = new RecordAudio();
                    started = true;
                    recordTask.execute();
                }
            }
        });
    }

    private void parse(String path) {
        Log.d("TAG ", "running");
        InputStream is = null;
        try {

            is = this.getContentResolver().openInputStream(Uri.parse(path));
            XMLPullParserHandler parser = new XMLPullParserHandler();
            score = parser.parse(is);
            Log.d("parser", "parsed");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class RecordAudio extends AsyncTask<Void, double[], Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                int bufferSize = AudioRecord.getMinBufferSize(frequency,
                        channelConfiguration, audioEncoding);

                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, frequency,
                        channelConfiguration, audioEncoding, bufferSize);

                short[] buffer = new short[blockSize];
                double[] toTransform = new double[blockSize];

                audioRecord.startRecording();

                while (started) {
                    int bufferReadResult = audioRecord.read(buffer, 0,
                            blockSize);

                    for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / 32768.0; // signed
                    }
                    transformer.ft(toTransform);
                    TreeMap<Double, Integer> map = new TreeMap<>();
                    for (int i = 0; i < toTransform.length; i++) {
                        map.put(toTransform[i], i);
                    }
                    if (map.lastKey() >= 10.0)
                        Log.d("buckets", String.valueOf(map.lastEntry()) + " " + map.keySet().toArray()[map.size() - 2] + "=" + map.values().toArray()[map.size() - 2]);
                    publishProgress(toTransform);

                }

                audioRecord.stop();

            } catch (Throwable t) {
                t.printStackTrace();
                Log.e("AudioRecord", "Recording Failed");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(double[]... toTransform) {

        }

    }
}
