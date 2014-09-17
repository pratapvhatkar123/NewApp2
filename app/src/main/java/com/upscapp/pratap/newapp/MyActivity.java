package com.upscapp.pratap.newapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MyActivity extends Activity {


  ListView listView;
    String [] days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        new getUrl().execute();






    }






















    protected class getUrl extends AsyncTask
    {

        ArrayAdapter ad ;

        public getUrl() {
            super();

        }

        @Override
        protected void onPreExecute() {
        }


        @Override
        public String doInBackground(Object[] objects) {

            DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httppost = new HttpGet("http://upsc.herokuapp.com/api/v1/books.json?category=CSAT&page=1");

            InputStream inputStream = null;
            String result = null;
            try {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();

                return result;
            } catch (Exception e) {
                // Oops
            }
            finally
            {
                try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
            }

            return result;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            System.out.println(o);
            JSONArray jArray=null;
            System.out.println(R.string.hello_world);
            try {
               JSONObject jsonObj =  new JSONObject((String)o);
                 jArray = jsonObj.getJSONArray("books");
                System.out.println(jArray.get(0));



            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ArrayList<String> arrayList = new ArrayList<String>();

            final ArrayList<String> arrayUrl = new ArrayList<String>();


            for(int i =0 ; i < jArray.length();i++)
            {
                String str = null;
                try {
                    str = (String) jArray.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String fileName = str.substring( str.lastIndexOf('/')+1, str.length() );
                String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
                arrayList.add(fileNameWithoutExtn);

                arrayUrl.add(str);
                System.out.print(str);

            }




            ListView l = (ListView)findViewById(R.id.ListView);

            System.out.print(arrayList);
                ad = new ArrayAdapter<String>(MyActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
            l.setAdapter(ad);



            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    // Show Alert
                    Toast.makeText(MyActivity.this,
                            "Downloading..."+arrayUrl.get(i), Toast.LENGTH_LONG)
                            .show();


                    System.out.print(arrayUrl.get(i).getClass());


                    String url = arrayUrl.get(i);
                    String fileName = arrayList.get(i);


                    String [] paramers = new String[0];
                    paramers = new String[]
                            {
                                  "http://www.pdf995.com/samples/pdf.pdf",fileName,"A"

                            };

                    new downloadTask().execute(paramers);

                }
            });

        }


        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);



        }

    }


    public class downloadTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {



            for (int i=0;i<strings.length;i++)
            {
                System.out.println(strings[i]);
            }
            HttpClient httpClient = new DefaultHttpClient();



            HttpGet httpGet = null;
            httpGet = new HttpGet(strings[0]);
            HttpResponse response = null;

            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = (HttpEntity) response.getEntity();

                if(entity!=null)
                {
                    File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    if (outputFile.exists()) {
                        outputFile.createNewFile();
                    }

                    File folder = new File(Environment.getExternalStorageDirectory() + "/PratapMain");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }


                    if (success) {

                        File file = new File(Environment.getExternalStorageDirectory() + "/book1/page2.html");
                        if (file.exists()) {
                            //open file
                            
                        }
                        else
                        {
                            //download file and open

                        }

                    }




                    InputStream inputStream = entity.getContent();
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, read);
                    }
                    fileOutputStream.close();
                    System.out.println("Downloded " + outputFile.length() + " bytes. " + entity.getContentType());

                    return null;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String o) {

            super.onPostExecute((String) o);

            Toast.makeText(MyActivity.this,
                    "Downloded", Toast.LENGTH_LONG)
                    .show();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
