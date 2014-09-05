package com.upscapp.pratap.newapp;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;


public class GetData extends AsyncTask {


    public GetData() {
        super();

    }

    @Override
    protected JSONObject doInBackground(Object[] objects) {

        HttpClient client;
        client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://upsc.herokuapp.com/api/v1/books.json?category=test&page=1");
        HttpResponse response = null;
        HttpEntity entity = null;
        try {

            response = client.execute(httpGet);
            entity = response.getEntity();


            System.out.print("Testing-->"+ entity.getContent().toString());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(" ExceptionDone");

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
