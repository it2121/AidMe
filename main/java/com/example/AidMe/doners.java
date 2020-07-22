package com.example.AidMe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuckboy.R;

import java.util.ArrayList;
import java.util.List;

public class doners extends AppCompatActivity {
    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doners);
        listView= (ListView)findViewById(R.id.list_view);
list=MainActivity.listm;





        adapter = new ArrayAdapter(doners.this,android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);



    }
    public void goback(View v){
        Intent intent = new Intent(doners.this, MainActivity.class);
        startActivity(intent);
    }
    public void goinsert(View v){
        Intent intent = new Intent(doners.this, insertDoners.class);
        startActivity(intent);
    }


    public void godel(View v ){

        deleting.dorn=1;
        Intent intent = new Intent(doners.this, deleting.class);
        startActivity(intent);

    }
}
