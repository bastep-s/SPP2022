package com.example.spp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int cntFl = 0; /// число полетов
    final static String nameVariableKey = "Группа годности:";

    private static final String PREFS_FILE = "Account";
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    EditText cntFlBox;
    EditText nameBox;
    EditText phoneBox;
    EditText ageBox;
    EditText rankBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        cntFlBox = findViewById(R.id.countBox);
        cntFlBox.setText(String.valueOf(settings.getInt("cntFl",1)));

        nameBox = findViewById(R.id.nameBox);
        nameBox.setText(settings.getString("name",""));

        phoneBox = findViewById(R.id.phoneBox);
        phoneBox.setText(settings.getString("phone",""));

        ageBox = findViewById(R.id.yearBox);
        ageBox.setText(String.valueOf(settings.getInt("age",21)));

        rankBox = findViewById(R.id.rankBox);
        rankBox.setText(settings.getString("rank",""));

    }

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putChar(nameVariableKey, rankBox.getText().toString().charAt(0));
        super.onSaveInstanceState(outState);
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rankBox.setText(Character.toString(savedInstanceState.getChar(nameVariableKey)));
        TextView dataView = findViewById(R.id.dataView);
        dataView.setText("Группа годности: " + rankBox.getText().toString());
    }

    public void saveData(View view) {
        // получаем введенные данные
        rankBox = findViewById(R.id.rankBox);
        try {
            rankBox.setText(rankBox.getText().toString());
        }catch (NumberFormatException ex) {
            rankBox.setText("");
        }
    }


    public void getData(View view) {
        // получаем сохраненные данные
        TextView dataView = findViewById(R.id.dataView);
        dataView.setText(("Группа годности: " + rankBox.getText().toString()));
    }

    /// сохранение данных между активностями (при выходе из приложения)
    @Override
    protected void onPause(){
        super.onPause();

        cntFlBox = findViewById(R.id.countBox);
        nameBox = findViewById(R.id.nameBox);
        phoneBox = findViewById(R.id.phoneBox);
        ageBox = findViewById(R.id.yearBox);
        rankBox = findViewById(R.id.rankBox);

        // сохраняем в настройках
        prefEditor = settings.edit();

        prefEditor.putInt("cntFl", Integer.parseInt(cntFlBox.getText().toString()));
        prefEditor.putString("name", nameBox.getText().toString());
        prefEditor.putString("phone", phoneBox.getText().toString());
        prefEditor.putInt("age",  Integer.parseInt(ageBox.getText().toString()));
        prefEditor.putString("rank", rankBox.getText().toString());

        SpaceMan newSpaceMan = new SpaceMan(nameBox.getText().toString(),rankBox.getText().toString().charAt(0),Integer.parseInt(cntFlBox.getText().toString()),Byte.parseByte(ageBox.getText().toString()),phoneBox.getText().toString());

        prefEditor.apply();
    }
}