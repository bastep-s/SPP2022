package com.example.ssp3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int itemID = -1;
    String[] epit;
    String[] nouns;
    String selectedNouns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        epit = getResources().getStringArray(R.array.epithets);
        nouns = getResources().getStringArray(R.array.nouns);

        // получаем элемент
        TextView selection = findViewById(R.id.selection);
        Button myButton = findViewById(R.id.button);

        RadioGroup radioGroup = findViewById(R.id.radios);

        Button addButton = findViewById(R.id.button2);
        EditText addText = findViewById(R.id.addvalue);

        Button deleteButton = findViewById(R.id.button3);

        CheckBox check = findViewById(R.id.checkBox);

        // получаем элемент
        ListView epithetsList = findViewById(R.id.epithetsList);
        Spinner spinner = findViewById(R.id.spinner);

        ArrayList<String> nounsArr = new ArrayList<String>();
        Collections.addAll(nounsArr , nouns);

        // создаем адаптер
        ArrayAdapter<String> adapter_list = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,epit);
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nounsArr);

        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // устанавливаем для списка адаптер
        epithetsList.setAdapter(adapter_list);
        spinner.setAdapter(adapter_spinner);

        epithetsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                itemID = position;
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String result = epit[itemID] + " " + selectedNouns + " " + radioButton.getText();

                if (check.isChecked()){
                    String temp="";
                    for(int i=0;i<result.length(); i++){
                        if(i%2==0){
                            temp+=result.substring(i,i+1).toUpperCase(Locale.ROOT);
                        }else{
                            temp+=result.substring(i,i+1).toLowerCase(Locale.ROOT);
                        }

                    }
                    result = temp;
                }

                selection.setText(result);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedNouns = parent.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                String newElem = addText.getText().toString();

                adapter_spinner.add(newElem);
                adapter_spinner.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter_spinner.remove(spinner.getSelectedItem().toString());
                adapter_spinner.notifyDataSetChanged();
            }
        });

        addText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // completion button is pressed, and where the corresponding upper imeOptions
                    adapter_spinner.remove(spinner.getSelectedItem().toString());
                    adapter_spinner.insert(addText.getText().toString(),(int)spinner.getSelectedItemId());
                    //spinner.setSelection(nouns.length-1);

                    adapter_spinner.notifyDataSetChanged();
                    return true;
                    // returns true, retains the soft keyboard. false, hiding the soft keyboard
                }
                return false;
            }
        });
    }
}