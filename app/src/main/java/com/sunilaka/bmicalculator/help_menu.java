package com.sunilaka.bmicalculator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class help_menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        // Add Back to Home Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
