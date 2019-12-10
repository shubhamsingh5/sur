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
import com.example.sur.model.Note;
import com.example.sur.model.Page;
import com.example.sur.model.Score;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlayMusic extends AppCompatActivity {

    private Button startPlaying;
    private Score score;
    private boolean flip;
    private int noteIndex;
    int frequency = 8000;
    int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RealDoubleFFT transformer;
    int blockSize = 512;

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
                            Log.d("new", n.getStep() + n.getOctave() + " " + n.getFrequency());
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

    public class RecordAudio extends AsyncTask<ArrayList<Note>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(ArrayList<Note>... notes) {

            try {
                int bufferSize = AudioRecord.getMinBufferSize(frequency,
                        channelConfiguration, audioEncoding);

                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, frequency,
                        channelConfiguration, audioEncoding, bufferSize);

                short[] buffer = new short[blockSize];
                double[] toTransform = new double[blockSize];

                audioRecord.startRecording();

                TreeMap<Double, Integer> maxMap = new TreeMap<>();
                ArrayList<Integer> maxArray = new ArrayList<>();

                while (started) {
                    int bufferReadResult = audioRecord.read(buffer, 0,
                            blockSize);

                    for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / 32768.0; // signed
                        // 16
                    }                                       // bit
                    transformer.ft(toTransform);
                    TreeMap<Double, Integer> map = new TreeMap<>();
                    for (int i = 0; i < toTransform.length; i++) {
                        map.put(toTransform[i], i);
                    }

                    if (Double.valueOf(1).compareTo(map.lastKey()) < 0) {
                        maxMap.put(map.lastKey(), map.get(map.lastKey()));
                        maxArray.add(map.get(map.lastKey()));
                    } else {
                        if (maxArray.size() > 2) {
                            ArrayList<Integer> top3 = new ArrayList<>();
                            top3.add(maxArray.get(maxArray.size() - 1));
                            top3.add(maxArray.get(maxArray.size() - 2));
                            top3.add(maxArray.get(maxArray.size() - 3));

                            if (arrayContainsAllPossiblePosOf(top3, 33))
                                Log.d("Notes mode array", "" + mode(maxArray));
                        }

                        maxMap.clear();
                        maxArray.clear();
                    }
                }
                audioRecord.stop();

            } catch (Throwable t) {
                t.printStackTrace();
                Log.e("AudioRecord", "Recording Failed");
            }
            //return true or false based on flipping page
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            noteIndex++;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            flip = aBoolean;
        }
    }

    public boolean arrayContainsAllPossiblePosOf(ArrayList<Integer> topThreePos, int exactPos) {
        return topThreePos.contains(exactPos - 1) || topThreePos.contains(exactPos) || topThreePos.contains(exactPos + 1) ||
                topThreePos.contains((exactPos - 1) * 2) || topThreePos.contains(exactPos * 2) || topThreePos.contains((exactPos + 1) * 2);
    }

    public HashMap<Integer, Integer> mode(ArrayList<Integer> array) {
        HashMap<Integer, Integer> hm = new HashMap<>();

        for (int i = 0; i < array.size(); i++) {

            if (hm.get(array.get(i)) != null) {

                int count = hm.get(array.get(i));
                count++;
                hm.put(array.get(i), count);
            } else
                hm.put(array.get(i), 1);
        }
        return sortByValues(hm);
    }

    private HashMap<Integer, Integer> sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}
