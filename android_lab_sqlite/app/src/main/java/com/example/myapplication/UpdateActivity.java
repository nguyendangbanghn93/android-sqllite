package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBHelper;


public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHelper db;
    private int _id;
    private EditText edName;
    private EditText edGender;
    private EditText edDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db = new DBHelper(this);
        initView();
        //fill data
        Intent intent = getIntent();
        _id = intent.getIntExtra(DBHelper.ID, 0);
        String name = intent.getStringExtra(DBHelper.NAME);
        String gender = intent.getStringExtra(DBHelper.GENDER);
        String description = intent.getStringExtra(DBHelper.DESCRIPTION);

        edName.setText(name);
        edGender.setText(gender);
        edDescription.setText(description);
    }

    private void initView() {
        edName = findViewById(R.id.editName);
        edGender = findViewById(R.id.editGender);
        edDescription = findViewById(R.id.editDescription);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDelete:
                onDelete();
                break;
            case R.id.btnUpdate:
                onUpdate();
                break;
            default:
                break;
        }
    }

    private void onUpdate() {
        String isUpdate =  db.updateUser(_id, edName.getText().toString(), edGender.getText().toString(),
                edDescription.getText().toString());
        Toast.makeText(this, isUpdate, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void onDelete() {
        String isDelete =  db.deleteUser(_id);
        Toast.makeText(this, isDelete, Toast.LENGTH_SHORT).show();
        finish();
    }
}