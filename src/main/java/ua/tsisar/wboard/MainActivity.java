package ua.tsisar.wboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button signIn;
    private Button register;
    private CheckBox rememberMe;

    private String stringUsername;
    private String stringPassword;

    private boolean booleanRememberMe;

    private final String CONTENT_TYPE = "application/json";

    private final String URL = "http://192.168.20.102:8080";
    private final String AUTHENTICATE = "/api/authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findView();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUsername = login.getText().toString();
                stringPassword = password.getText().toString();
                booleanRememberMe = rememberMe.isChecked();

                new RequestTask().execute();
            }
        });
    }

    private void findView(){
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        register = (Button) findViewById(R.id.register);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

    }

    class RequestTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection = null;
        DataOutputStream wr = null;
        String result = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                //создаем запрос на сервер
                URL url = new URL(URL + AUTHENTICATE);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                JSONObject jo = new JSONObject();

                jo.put("password", stringPassword);
                jo.put("rememberMe", booleanRememberMe);
                jo.put("username", stringUsername);

                wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(jo.toString());
                wr.flush();
                wr.close();

                int responseCode = urlConnection.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String inputLine;

                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
    }
}