package com.example.medicaldiagnostics;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medicaldiagnostics.sections.Bones;
import com.example.medicaldiagnostics.sections.Heart;
import com.example.medicaldiagnostics.sections.NervousSystem;
import com.example.medicaldiagnostics.sections.Oncology;
import com.example.medicaldiagnostics.sections.RespiratoryOrgans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Favorites extends Fragment {

    ArrayList<String> nameList = new ArrayList<>();

    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    String selectedItem;
    String selectedWord;
    ArrayList<String> result;

    SharedPreferences.Editor ed;
    SharedPreferences pref;

    boolean sign = true;


    public boolean isDuplicate(ArrayList<String> arrayList, String name){
        for (String counter: arrayList){
            if (counter.equals(name)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, nameList);
        mainListView = (ListView) getView().findViewById(R.id.main_listview);
        mainListView.setAdapter(mArrayAdapter);
        registerForContextMenu(mainListView);

        ed = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (sign){
            sign = false;
            nameList.addAll(pref.getAll().keySet());
            mArrayAdapter.notifyDataSetChanged();
        }

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                result = bundle.getStringArrayList("1");
                if (result != null){
                    for (String i: result) {
                        if (isDuplicate(nameList, i)) {
                            ed.putString(i, i);
                            ed.commit();
                            Log.i("SPREF", i);
                            nameList.add(i);
                            }
                        }
                    }
                mArrayAdapter.notifyDataSetChanged();
            }
        });


        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selectedItem =  (String)parent.getItemAtPosition(position);
                switch (selectedItem) {
                    case "Болезни органов дыхахния":
                        Intent intent1 = new Intent(getActivity(), RespiratoryOrgans.class);
                        startActivity(intent1);
                        break;
                    case "Заболевания нервной системы":
                        Intent intent2 = new Intent(getActivity(), NervousSystem.class);
                        startActivity(intent2);
                        break;
                    case "Болени костей":
                        Intent intent3 = new Intent(getActivity(), Bones.class);
                        startActivity(intent3);
                        break;
                    case "Сердечно-сосудистые заболевания":
                        Intent intent4 = new Intent(getActivity(), Heart.class);
                        startActivity(intent4);
                        break;
                    case "Онкологические заболевания":
                        Intent intent5 = new Intent(getActivity(), Oncology.class);
                        startActivity(intent5);
                        break;
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedWord = ((TextView) info.targetView).getText().toString();
        menu.add(0, 1, 0, "Удалить из избранного");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        nameList.remove(selectedWord);
        ed.remove(selectedWord);
        ed.commit();
        mArrayAdapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

}