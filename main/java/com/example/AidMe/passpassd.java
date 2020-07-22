package com.example.AidMe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuckboy.R;

public class passpassd extends AppCompatActivity {
TextView passtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passpassd);
        passtext= (TextView)findViewById(R.id.passtext);
        String pass = insertDoners.pass;

        passtext.setText("Your PassCode if ("+pass+") You will be needing it in case of removing.");


    }

    public void ok (View v){
        insertDoners.pass="";
        Intent intent = new Intent(passpassd.this, MainActivity.class);
    startActivity(intent);



    }
}
