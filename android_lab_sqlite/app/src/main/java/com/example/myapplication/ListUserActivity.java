package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBHelper;


public class ListUserActivity extends AppCompatActivity {
    private DBHelper db;
    private Cursor cursor;
    private SimpleCursorAdapter simpleCursorAdapter;
    private ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        db = new DBHelper(this);
        lvUsers = findViewById(R.id.lbUsers);
        cursor = db.getAllUsers();
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_user, cursor, new String[]{
                DBHelper.ID, DBHelper.NAME, DBHelper.GENDER}, new int[]{R.id.tvId, R.id.tvName, R.id.tvGender},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvUsers.setAdapter(simpleCursorAdapter);
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(i);
                int _id = cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
                String gender = cursor.getString(cursor.getColumnIndex(DBHelper.GENDER));
                String description = cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION));

                Intent intent = new Intent(ListUserActivity.this, UpdateActivity.class);
                intent.putExtra(DBHelper.ID, _id);
                intent.putExtra(DBHelper.NAME, name);
                intent.putExtra(DBHelper.GENDER, gender);
                intent.putExtra(DBHelper.DESCRIPTION, description);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //reload data
        Cursor c = db.getAllUsers();
        simpleCursorAdapter.changeCursor(c);
        simpleCursorAdapter.notifyDataSetChanged();
        db.close();
    }
}