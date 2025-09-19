package com.example.listycitylab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener { // NEW

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    // NEW: callback for EditCityFragment
    @Override
    public void editCity(City city) {
        cityAdapter.notifyDataSetChanged(); // the City is already modified in place
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // NEW: open editor when a list item is tapped
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City clickedCity = dataList.get(position);
            EditCityFragment editFrag = EditCityFragment.newInstance(clickedCity);
            editFrag.show(getSupportFragmentManager(), "Edit City");
        });
    }
}
