package com.bootsysoftware.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "c62c5864bec90aa70b94605fb5490a04";
        double lat =37.8267;
        double lon =-122.423;
        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/" + lat + "," + lon;
        //String forecastURL = "https://api.forecast.io/forecast/c62c5864bec90aa70b94605fb5490a04/37.8267,-122.423";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(forecastURL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if(response.isSuccessful()) {
                        Log.v(TAG, response.body().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });//end call.enqueue


    }//end onCreate
}//end class

