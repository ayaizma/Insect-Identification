package org.tensorflow.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadInsect extends Activity{

    private EditText editTextId;
    private EditText editTextNameL;
    private EditText editTextNameU;
    private EditText editTextHam;
    private EditText editTextCar;

    private Button buttonMenu;
    private Button buttonScan;

    private String h_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_insect);

        Intent intent = getIntent();

        h_id = intent.getStringExtra(konfigurasi.HAM_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextNameL = (EditText) findViewById(R.id.editTextNameL);
        editTextNameU = (EditText) findViewById(R.id.editTextNameU);
        editTextHam = (EditText) findViewById(R.id.editTextHam);
        editTextCar = (EditText) findViewById(R.id.editTextCar);

        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonScan = (Button) findViewById(R.id.buttonScan);

        editTextId.setText(h_id);

        getInsect();

        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonScan = (Button) findViewById(R.id.buttonScan);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReadInsect.this,MainActivity.class);
                startActivity(i);
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReadInsect.this,ReadAllInsect.class);
                startActivity(i);
            }
        });
    }

    private void getInsect(){
        class GetInsect extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ReadInsect.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showInsect(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,h_id);
                return s;
            }

        }
        GetInsect ge = new GetInsect();
        ge.execute();
    }

    private void showInsect(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name_l = c.getString(konfigurasi.TAG_NAMALATIN);
            String name_u = c.getString(konfigurasi.TAG_NAMAUMUM);
            String hama_p = c.getString(konfigurasi.TAG_HAMAPADA);
            String cara_b = c.getString(konfigurasi.TAG_CARABASMI);

            editTextNameL.setText(name_l);
            editTextNameU.setText(name_u);
            editTextHam.setText(hama_p);
            editTextCar.setText(cara_b);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
