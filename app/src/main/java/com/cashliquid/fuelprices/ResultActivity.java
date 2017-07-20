package com.cashliquid.fuelprices;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class ResultActivity extends AppCompatActivity {
    AsyncTask<Void, Void, Void> mTask;
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String citydata = getIntent().getStringExtra("CITY");
        final String fueldata = getIntent().getStringExtra("FUEL");
        String cityWithoutSpace=citydata.replaceAll("\\s+","");

        TextView city=(TextView) findViewById(R.id.textView3);
        TextView fuel=(TextView) findViewById(R.id.textView4);
        final TextView price=(TextView) findViewById(R.id.textView5);

        final String link="https://fuelpriceindia.herokuapp.com/price?city=" + cityWithoutSpace;

        mTask = new AsyncTask<Void, Void, Void> () {
            ProgressDialog dialog = new ProgressDialog(ResultActivity.this);
            @Override
            protected void onPreExecute() {
                // what to do before background task
                dialog.setTitle("Loading...");
                dialog.setMessage("Please wait.");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    jsonString = getJsonFromServer(link);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                try {
                    JSONObject json = new JSONObject(jsonString.toString());
                    if(fueldata.equals("diesel")){
                        price.append("₹ "+json.getString("diesel"));
                    }
                    else if(fueldata.equals("petrol")){
                        price.append("₹ "+json.getString("petrol"));
                    }
                    else {
                        price.append("₹ 0.00");
                    }
                    dialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        mTask.execute();
        city.append(citydata);
        fuel.append(fueldata);
    }
    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

}
