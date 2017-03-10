package com.example.amber.testingnotes;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlaySound extends Activity {
    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private final int duration = 1; // half seconds
    private final int sampleRate = 2000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone = 440; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];

    //Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");

        switch (note) {
            case ("1"):
                freqOfTone = 300;
                break;
            case ("2"):
                freqOfTone = 400;
                break;
            case ("3"):
                freqOfTone = 500;
                break;
            default:
                freqOfTone = 600;
        }

        try {
            playNotes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        for (int i = 0; i < 3; i++) {
            freqOfTone += 100;
            // Use a new tread as this can take a while
            final Thread thread = new Thread(new Runnable() {
                public void run() {
                    genTone();
                    handler.post(new Runnable() {

                        public void run() {
                            playSound();
                        }
                    });
                }
            });
            thread.start();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    void playNotes() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            freqOfTone += 100;
            genTone();
            playSound();
            Thread.sleep(duration*1000);
        }
    }

    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }
    void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }
}
