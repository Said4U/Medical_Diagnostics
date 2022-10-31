package com.example.medicaldiagnostics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.medicaldiagnostics.sections.Bones;
import com.example.medicaldiagnostics.sections.Heart;
import com.example.medicaldiagnostics.sections.NervousSystem;
import com.example.medicaldiagnostics.sections.Oncology;
import com.example.medicaldiagnostics.sections.RespiratoryOrgans;

import java.util.ArrayList;

public class SuitableDiseases extends AppCompatActivity {

    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitable_diseases);
        ArrayList<String> diseasesArray = getIntent().getStringArrayListExtra("hello");
        mainListView = findViewById(R.id.suitableList);
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diseasesArray);
        mainListView.setAdapter(mArrayAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                switch (selectedItem) {
                    case "Болезни органов дыхахния":
                        Intent intent1 = new Intent(getBaseContext(), RespiratoryOrgans.class);
                        startActivity(intent1);
                        break;
                    case "Заболевания нервной системы":
                        Intent intent2 = new Intent(getBaseContext(), NervousSystem.class);
                        startActivity(intent2);
                        break;
                    case "Болени костей":
                        Intent intent3 = new Intent(getBaseContext(), Bones.class);
                        startActivity(intent3);
                        break;
                    case "Сердечно-сосудистые заболевания":
                        Intent intent4 = new Intent(getBaseContext(), Heart.class);
                        startActivity(intent4);
                        break;
                    case "Онкологические заболевания":
                        Intent intent5 = new Intent(getBaseContext(), Oncology.class);
                        startActivity(intent5);
                        break;
                }
            }
        });
    }
}