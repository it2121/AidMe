package com.example.AidMe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AidMe.Models.TestModel;
import com.example.AidMe.Retrofit.ApiClient;
import com.example.AidMe.Retrofit.ApiInterface;
import com.example.fuckboy.R;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class insertNeeders extends AppCompatActivity {
    EditText nametext,phonetext,donertext,aget;
    ApiInterface apiInterfacen ;
  //  public static String pass ="ppl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_needers);
        nametext = (EditText) findViewById(R.id.nametext);
        phonetext = (EditText) findViewById(R.id.phonetext);
        donertext = (EditText) findViewById(R.id.donatingtext);
        aget = (EditText) findViewById(R.id.agetext);

    }


    public void submit(View v){
        String name ,phone,donating,age;

        name=nametext.getText().toString();
        phone=phonetext.getText().toString();
        donating=donertext.getText().toString();
        age=aget.getText().toString();

        StringBuilder sb = new StringBuilder();
        int n = 6;
        String set ="1234567890qwertyuioplkmjnhbgvfcdxsza";
        Random rand = new Random();
        int k =0;
        for (int i= 0; i < n; i++) {

            k= rand.nextInt(set.length()-1);

            sb.append(set.charAt(k));
        }
        insertDoners.pass = sb.toString();
       // String pass = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
        String lon ="0",lat="0";

        apiInterfacen = ApiClient.getApiClient().create(ApiInterface.class);

        Call<TestModel> insertdata = apiInterfacen.insertneed(name,phone,age,donating,insertDoners.pass,lon,lat);
        insertdata.enqueue(new Callback<TestModel>() {
            @Override
            public void onResponse(Call<TestModel> call, Response<TestModel> response) {

            }

            @Override
            public void onFailure(Call<TestModel> call, Throwable t) {

            }
        });
        Intent intent = new Intent(insertNeeders.this, passpassd.class);
        startActivity(intent);





    }
    public void back (View v){
        Intent intent = new Intent(insertNeeders.this, MainActivity.class);
        startActivity(intent);
    }

}
