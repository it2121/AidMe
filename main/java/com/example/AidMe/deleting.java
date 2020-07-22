package com.example.AidMe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AidMe.Models.TestModel;
import com.example.AidMe.Retrofit.ApiClient;
import com.example.AidMe.Retrofit.ApiInterface;
import com.example.fuckboy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class deleting extends AppCompatActivity {
    public static int dorn=0;
    EditText editText;
    ApiInterface apiInterfacey;
    //ApiInterface apiInterfaceyo;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting);
        this.editText = (EditText) findViewById(R.id.editText);
        this.textView = (TextView) findViewById(R.id.messtext);
       // apiInterfaceyo = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterfacey = ApiClient.getApiClient().create(ApiInterface.class);

    }

    public void submit (View v) {


        if(dorn==1)
        {

            String o = editText.getText().toString();

            Call<TestModel> deldon = apiInterfacey.deletingdoner(o+"");


            deldon.enqueue(new Callback<TestModel>() {
                @Override
                public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                    if(response.body()!=null){

                        TestModel tt = response.body();
                        if(tt.getWhat() ==1){

                            textView.setText("the record has been removed successfully.");
                        }else if(tt.getWhat() == 2){
                            textView.setText("Failed, The record does not exist.");

                        }

                    }
                }

                @Override
                public void onFailure(Call<TestModel> call, Throwable t) {

                }
            });


        }
        else if(dorn ==2){
            String o = editText.getText().toString();
            Call<TestModel> deldon1 = apiInterfacey.deletingneeders(o+"");
            deldon1.enqueue(new Callback<TestModel>() {
                @Override
                public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                    if(response.body()!=null){

                        TestModel tt = response.body();
                        if(tt.getWhat()==1){

                            textView.setText("the record has been removed successfully.");
                        }else if(tt.getWhat()==2){
                            textView.setText("Failed, The record does not exist.");

                        }

                    }
                }

                @Override
                public void onFailure(Call<TestModel> call, Throwable t) {

                }
            });



        }else{
            textView.setText("null");
        }

    }
    public void deld(){

    }

    public void back (View v) {
        Intent intent = new Intent(deleting.this, MainActivity.class);
        startActivity(intent);

    }
}
