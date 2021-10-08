package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edName;
    private EditText edDescription;
    private Button btnRegister;
    private Spinner spinner;
    private DBHelper dbHelper;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edName = findViewById(R.id.edUsername);
        edDescription = findViewById(R.id.edDescription);
        btnRegister = findViewById(R.id.btnRegister);
        spinner = findViewById(R.id.spinner);
        dbHelper = new DBHelper(this);
        checkBox = findViewById(R.id.checkbox);
        String[] genders = {"male", "female", "other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genders);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRegister) {
            onRegister();
        }
    }

    private void onRegister() {
        if (edName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please agree rules", Toast.LENGTH_LONG).show();
            return;
        }
        String isAdd = dbHelper.addUser(edName.getText().toString(), spinner.getSelectedItem().toString(), edDescription.getText().toString());
        Toast.makeText(this, isAdd, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ListUserActivity.class);
        startActivity(intent);
    }
}