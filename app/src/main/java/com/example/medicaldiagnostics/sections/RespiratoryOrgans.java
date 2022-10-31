package com.example.medicaldiagnostics.sections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.medicaldiagnostics.R;

public class RespiratoryOrgans extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respiratory_organs);
        textView = findViewById(R.id.resp_text);
        textView.setMovementMethod(new ScrollingMovementMethod());


    }
}