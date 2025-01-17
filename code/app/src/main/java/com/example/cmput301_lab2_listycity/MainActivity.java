package com.example.cmput301_lab2_listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> cityList;
    private ArrayAdapter<String> adapter;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView and data
        ListView listView = findViewById(R.id.cityList);
        Button addCityButton = findViewById(R.id.addCityButton);
        Button deleteCityButton = findViewById(R.id.deleteCityButton);

        cityList = new ArrayList<>();
        cityList.add("Edmonton");
        cityList.add("Calgary");
        cityList.add("Vancouver");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(adapter);

        // Handle adding cities
        addCityButton.setOnClickListener(v -> showAddCityDialog());

        // Handle selecting a city for deletion
        listView.setOnItemClickListener((parent, view, position, id) -> selectedCity = cityList.get(position));

        // Handle deleting cities
        deleteCityButton.setOnClickListener(v -> {
            if (selectedCity != null) {
                cityList.remove(selectedCity);
                adapter.notifyDataSetChanged();
                selectedCity = null;
                Toast.makeText(MainActivity.this, "City deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No city selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_city, null);
        builder.setView(dialogView);

        EditText cityNameInput = dialogView.findViewById(R.id.cityNameInput);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(v -> {
            String cityName = cityNameInput.getText().toString();
            if (!cityName.isEmpty()) {
                cityList.add(cityName);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "City added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
