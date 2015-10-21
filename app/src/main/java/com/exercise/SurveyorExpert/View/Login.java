package com.exercise.SurveyorExpert.View;

/**
 * Login screen created by Calum Macaskill on 25/11/2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.net.ConnectivityManager;

import com.exercise.AndroidViewPager.R;
import com.exercise.SurveyorExpert.AndroidMainController;
import com.exercise.SurveyorExpert.Model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

//import android.graphics.Color;
//import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;

    private EditText user, pass;
    private Button mSubmit, mRegister;
    private String userName, user_id, domain, ONLINE;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // php login script location:
    private static final String LOGIN_URL =
            "http://www.surveyorexpert.com/webservice/AndroidLogin.php";

    // JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DOMAIN = "domain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_login);

        // setup input fields
        user = (EditText) findViewById(R.id.etLoginUsername);
        pass = (EditText) findViewById(R.id.etLoginPassword);

        // setup buttons
        mSubmit = (Button) findViewById(R.id.bLoginLogin);
        mRegister = (Button) findViewById(R.id.bLoginRegister);

        // register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        message();
        switch (v.getId()) {
            case R.id.bLoginLogin:

                new TryLogin().execute();
                break;
            case R.id.bLoginRegister:
           //     Intent i = new Intent(this, Register.class);
            //    i.putExtra("userName", userName);
           //     startActivity(i);
                break;
            default:
                break;
        }
    }

    class TryLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute( ) {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this, AlertDialog.THEME_HOLO_DARK);
            pDialog.setMessage("Attempting Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Check for success tag
            Intent i;
            int success = 0;
            JSONObject json = new JSONObject();
            String username = user.getText().toString();
            String password = pass.getText().toString();
            userName = username;
            preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
            spEditor = preferences.edit();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                Log.d("SurveyorExpert", "Attempt Login");

                // getting product details by making HTTP request
                try{
                    Log.d("SurveyorExpert", "Look for internet ");
                    if( isInternetOn()) {
                        Log.d("SurveyorExpert", "Internet connection OK");
                        json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                        success = json.getInt(TAG_SUCCESS);
                        ONLINE = "true";
                    }
                    else{
                        Log.d("SurveyorExpert", "Internet connection FAILED");
                        ONLINE = "Not connected to internet";
                        success = 0;
                    }


                 //   Toast.makeText(Login.this, "Attempt Login", Toast.LENGTH_LONG).show();

                } catch(Exception e){
                    ONLINE = "false";
                    success = 0;
                    Log.d("SurveyorExpert", "Login failed");
                    spEditor.putString("ONLINE", "false");
                    spEditor.apply();
                    //	e.printStackTrace();
                }
                if (success == 1) {

                    user_id = json.getString(TAG_MESSAGE);
                    domain = json.getString(TAG_DOMAIN);

                    if (domain.contains("ADMIN"))
                    {// get rid of warning
                     //   i = new Intent(Login.this, AdminIntroduction.class);
                        i = new Intent(Login.this, AndroidMainController.class);
                    }
                    else
                    {
                        i = new Intent(Login.this, AndroidMainController.class);
                    }

                    Log.d("SurveyorExpert","Login with LOGIN_URL " +  LOGIN_URL);
                    Log.d("SurveyorExpert","Login with user_name " +  userName);
                    Log.d("SurveyorExpert","Login with user_id " +  user_id);
                    Log.d("SurveyorExpert","Login with domain " +  domain);
                    Log.d("SurveyorExpert","Login with ONLINE has been set to " +  ONLINE);


                    spEditor.putString("userName", userName);
                    spEditor.putString("userId", user_id);
                    spEditor.putString("domain", domain);
                    spEditor.putString("ONLINE", ONLINE);

                    spEditor.apply();

                    Log.d("SurveyorExpert","Login Ok with " +  json.getString(TAG_MESSAGE));
                    finish();
                    startActivity(i);

                    return json.getString(TAG_MESSAGE);
                } else {

                    Log.d("SurveyorExpert", "Login failed starting admin");
                    //	Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    // to get rid of warning
                    // i = new Intent(Login.this, AdminIntroduction.class);
                    i = new Intent(Login.this, AndroidMainController.class);
                    finish();
                    startActivity(i);
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
/*
			Toast.makeText(Login.this,
					"Login \n userName = " + userName +
					"\n user_id = " + user_id +
					"\n domain = " + domain +
					"\n ONLINE = " + ONLINE ,
					Toast.LENGTH_LONG).show();
*/
            pDialog.dismiss();
        }
    }


    public final boolean isInternetOn() {

        Log.d("SurveyorExpert", "In method isInternetOn ");
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                null;
        try {
            connec = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
            Log.d("SurveyorExpert", "In method isInternetOn getting context " + connec.toString());
        } catch (Exception e) {
            Log.d("SurveyorExpert", "In method isInternetOn exception getting context");
            e.printStackTrace();
        }

        // Check for network connections

        try {
            Log.d("SurveyorExpert", "LOOK AT THIS - OK ");

            Log.d("SurveyorExpert", " android.net.NetworkInfo.State.CONNECTED = " +  android.net.NetworkInfo.State.CONNECTED );
            Log.d("SurveyorExpert", " android.net.NetworkInfo.State.CONNECTED = " +  android.net.NetworkInfo.State.CONNECTING);
            Log.d("SurveyorExpert", " connec.getNetworkInfo(1).getState() " +  connec.getNetworkInfo(0).getState().toString());
            Log.d("SurveyorExpert", " connec.getNetworkInfo(1).getState() " +  connec.getNetworkInfo(0).getState().toString());
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

                // if connected with internet
                Log.d("SurveyorExpert", "In method isInternetOn OK ");
            //    Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                return true;

            } else if (
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
                Log.d("SurveyorExpert", "In method isInternetOn FAIL");
              //  Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            Log.d("SurveyorExpert", "In method isInternetOn FAIL");
          //  e.printStackTrace();
        }
        return false;
    }


    void message(){
        /*
        Toast.makeText(Login.this,
                "Login \n userName = " + userName +
                        "\n user_id = " + user_id +
                        "\n domain = " + domain +
                        "\n ONLINE = " + ONLINE,
                Toast.LENGTH_LONG).show();
                */
    }
}
