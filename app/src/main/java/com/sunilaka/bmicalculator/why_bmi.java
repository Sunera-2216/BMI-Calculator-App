package com.sunilaka.bmicalculator;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class why_bmi extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.why);

        // Add link in text view
        TextView text = (TextView) findViewById(R.id.whybmi);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        // Add Back to Home Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
