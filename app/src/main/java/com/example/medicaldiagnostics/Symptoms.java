package com.example.medicaldiagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Symptoms extends Fragment {

    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    String selectedItem;
    Button button;
    ArrayList<String> selectedSymptoms = new ArrayList<>();
    ArrayList<String> suitableDiseases = new ArrayList<>();

    String[] allSymptoms = new String[]{"Одышка", "Кашель", "Кровохарканье", "Боль в груди", "Повышенная температура",
            "Головокружение", "Шум в ушах", "Повышенная утомляемость", "Бессонница", "Расстройство памяти", "Судороги", "Боль в голове и других участках тела",
            "Раннее насыщение при еде", "Боли в костях и суставах", "Сложность в движениях", "Отек вокруг сустава", "Повышенная температура в районе сустава",
            "Кашель, усиливающийся в лежачем положении", "Изменение частоты и ритма пульса", "Стабильно повышенное или пониженное кровяное давление", "Удушье", "Обмороки без причины",
            "Повышенная утомляемость", "Снижение работоспособности", "Нарушение сна", "Нарушение питания", "Быстрая потеря веса"};

    String[] symptomsBreathing = new String[]{"Одышка", "Кашель", "Кровохарканье", "Боль в груди", "Повышенная температура" };
    String[] symptomsNervous = new String[]{"Головокружение", "Шум в ушах", "Повышенная утомляемость", "Бессонница", "Расстройство памяти", "Судороги", "Боль в голове и других участках тела"};
    String[] symptomsBones = new String[]{"Раннее насыщение при еде", "Боли в костях и суставах", "Сложность в движениях", "Отек вокруг сустава", "Повышенная температура в районе сустава"};
    String[] symptomsHeart = new String[]{"Кашель, усиливающийся в лежачем положении", "Изменение частоты и ритма пульса", "Стабильно повышенное или пониженное кровяное давление", "Удушье", "Обмороки без причины"};
    String[] symptomsOncology = new String[]{"Повышенная утомляемость", "Снижение работоспособности", "Нарушение сна", "Нарушение питания", "Быстрая потеря веса"};

    Map<String, String[]> diseasesDict = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        button = (Button) view.findViewById(R.id.button);
        mainListView = getView().findViewById(R.id.list);
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, allSymptoms);
        mainListView.setAdapter(mArrayAdapter);
        registerForContextMenu(mainListView);

        diseasesDict.put("Болезни органов дыхахния", symptomsBreathing);
        diseasesDict.put("Болезни нервной системы", symptomsNervous);
        diseasesDict.put("Болезни костей", symptomsBones);
        diseasesDict.put("Сердечно-сосудистые заболевания", symptomsHeart);
        diseasesDict.put("Онкологические заболевания", symptomsOncology);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                boolean sign = true;
                selectedItem = (String) parent.getItemAtPosition(position);
                for (String word : selectedSymptoms) {
                    if (selectedItem.equals(word)) {
                        sign = false;
                        break;
                    }
                }
                if (sign){
                    selectedSymptoms.add(selectedItem);
                }else{
                    selectedSymptoms.remove(selectedItem);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suitableDiseases.clear();
                int countIteration = -1;
                for (String[] lst: diseasesDict.values()) {
                    float countSymptoms = 0;
                    countIteration++;
                    for (String word: lst) {
                        for (String symptom: selectedSymptoms) {
                            if (symptom.equals(word)){
                                countSymptoms++;
                            }
                        }
                    }
                    if (countSymptoms/lst.length >= 0.7){
                        suitableDiseases.add((String) diseasesDict.keySet().toArray()[countIteration]);
                    }
                }
                if (suitableDiseases.size() == 0){
                    Toast.makeText(getContext(), "Не удалось определить заболевание", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), SuitableDiseases.class);
                    intent.putStringArrayListExtra("hello", suitableDiseases);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.symptoms, null);
    }
}