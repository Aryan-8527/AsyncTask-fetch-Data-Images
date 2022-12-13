package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    List<Model_class> list;
    RecyclerView recyclerView;
    private static String Json_url = "https://foysal-official.web.app/fb_status.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<>();

        GetData getData = new GetData();
        getData.execute();
    }

    private class GetData extends AsyncTask<String , String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Aryan", "onPreExecute: ");
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Load Some Data ..!!!");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d("Aryan", "doInBackground: ");
            String current = " ";
            try {
                URL url ;
                HttpsURLConnection urlConnection = null ;
                try {
                    url = new URL(Json_url);
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();

                    while (data!= -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                        return  current;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Aryan", "onPostExecute: ");
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("smslist");


                for (int i=0; i< jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Model_class model_class = new Model_class();

                    model_class.setTitle(jsonObject1.getString("name"));
                    model_class.setDetails(jsonObject1.getString("details"));
                    model_class.setImage(jsonObject1.getString("image"));
                    list.add(model_class);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutdataIntoRecyclerView(list);
        }
    }

    private void PutdataIntoRecyclerView(List<Model_class> list) {
        CustomAdapter adapter = new CustomAdapter(this , list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}