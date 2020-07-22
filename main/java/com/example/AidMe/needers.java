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

public class needers extends AppCompatActivity {
    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needers);

        listView= (ListView)findViewById(R.id.list_view);
        list=MainActivity.listm;






        adapter = new ArrayAdapter(needers.this,android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);



    }
    public void goback(View v){
        Intent intent = new Intent(needers.this, MainActivity.class);
        startActivity(intent);
    }
    public void goinsert(View v){
        Intent intent = new Intent(needers.this, insertNeeders.class);
        startActivity(intent);
    }
    public void godel(View v ){

        deleting.dorn=2;
        Intent intent = new Intent(needers.this, deleting.class);
        startActivity(intent);

    }
}
