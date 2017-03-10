package com.example.amber.testingnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play1(View view) {
        //change view to FullScreen activity
        Intent intent = new Intent(this, PlaySound.class);
        intent.putExtra("note", "1");
        startActivity(intent);
    }

    public void play2(View view) {
        //change view to FullScreen activity
        Intent intent = new Intent(this, PlaySound.class);
        intent.putExtra("note", "2");
        startActivity(intent);
    }

    public void play3(View view) {
        //change view to FullScreen activity
        Intent intent = new Intent(this, PlaySound.class);
        intent.putExtra("note", "3");
        startActivity(intent);
    }
}
