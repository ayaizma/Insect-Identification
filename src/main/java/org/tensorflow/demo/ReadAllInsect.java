package org.tensorflow.demo;

import android.app.Activity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class
ReadAllInsect extends Activity implements ListView.OnItemClickListener{

    private ListView listView;
    private String JSON_STRING;
    Button b_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all_insect);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();

        b_menu = (Button) findViewById(R.id.b_main);

        b_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReadAllInsect.this,MainActivity.class);
                startActivity(i);
            }
        });
    }


    private void showInsect(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_h = jo.getString(konfigurasi.TAG_IDHAMA);
                String name_l = jo.getString(konfigurasi.TAG_NAMALATIN);
                String name_u = jo.getString(konfigurasi.TAG_NAMAUMUM);

                HashMap<String,String> insects = new HashMap<>();
                insects.put(konfigurasi.TAG_IDHAMA,id_h);
                insects.put(konfigurasi.TAG_NAMALATIN,name_l);
                insects.put(konfigurasi.TAG_NAMAUMUM,name_u);
                list.add(insects);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ReadAllInsect.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_IDHAMA,konfigurasi.TAG_NAMALATIN,konfigurasi.TAG_NAMAUMUM},
                new int[]{R.id.id_h, R.id.name_l, R.id.name_u});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ReadAllInsect.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showInsect();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ReadInsect.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String hamId = map.get(konfigurasi.TAG_IDHAMA);
        intent.putExtra(konfigurasi.HAM_ID,hamId);
        startActivity(intent);
    }
}
