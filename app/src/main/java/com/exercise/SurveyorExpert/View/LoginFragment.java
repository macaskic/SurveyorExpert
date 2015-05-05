//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class LoginFragment {
//}

package com.exercise.SurveyorExpert.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;
import com.exercise.SurveyorExpert.AndroidMainController;
import com.exercise.SurveyorExpert.Model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class LoginFragment extends Fragment implements OnClickListener, TabHost.OnTabChangeListener{

    private EditText user, pass;
    private Button mSubmit, mRegister;
    private String userName, userId, domain, ONLINE;
    private Exception myEx = null;
    private String msg = null;

    // Progress Dialog
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();

    // php login script location:
    private static final String LOGIN_URL =
            "http://www.surveyorexpert.com/webservice/AndroidLogin.php";

    // JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DOMAIN = "domain";

    private View rootView = null;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


      //  AndroidMainActivity.TabsAdapter = getActivity().getTabsAdapter();
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // setup input fields
        user = (EditText) rootView.findViewById(R.id.etLoginUsername);
        pass = (EditText) rootView.findViewById(R.id.etLoginPassword);

        // setup buttons
        mSubmit = (Button) rootView.findViewById(R.id.bLoginLogin);
        mRegister = (Button) rootView.findViewById(R.id.bLoginRegister);

        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        Log.d("Login Fragment", "Attempt Login");

        preferences =  this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();

  //      Toast.makeText(getActivity(), "Login Fragment", Toast.LENGTH_SHORT).show();
        if (preferences != null) {
            userId = preferences.getString("userId", "");
            userName = preferences.getString("userName", "");
            domain = preferences.getString("domain", "");
            ONLINE = preferences.getString("ONLINE", "");
            //       msg = "OK" + ONLINE + " end";
        } else {
            //       msg = "Shit";
            Toast.makeText(getActivity(), "preferences is null", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), "Got Login Activity : " + ONLINE
                + "\n message: " + msg
                + "\n userName: " + userName
                + "\n userId: " + userId
                + "\n domain: " + domain
                , Toast.LENGTH_SHORT).show();


        return rootView;
    }

    public void onTabChanged(String tabId) {
        Toast.makeText(getActivity(), "Selected Tab "+tabId, Toast.LENGTH_LONG).show();
        //  Log.i("selected tab index", "Current index - "+ mTabHost.getCurrentTab());
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bLoginLogin:
                new TryLogin().execute();
                ((AndroidMainController)getActivity()).switchFragment(1);
                break;
            case R.id.bLoginRegister:
              //  Toast.makeText(getActivity(), "Not implemented", Toast.LENGTH_SHORT).show();
                ((AndroidMainController)getActivity()).switchFragment(11);
                break;
            default:
                break;
        }
    }

    class TryLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute( ) {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_DARK);
            pDialog.setMessage("Attempting Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Check for success tag
         //   Intent i = null;
            Intent i;
            JSONObject json = new JSONObject();
            int success = 0;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            userName = username;


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
               	Log.d("Login Fragment", "Attempt Login");

                // getting product details by making HTTP request
                try{

                    json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",params);
                    success = json.getInt(TAG_SUCCESS);
                    editor.putString("ONLINE","true");
                    editor.apply();
                    ONLINE = "true";

                } catch(Exception e){
                    ONLINE = "false";
                    Log.d("Login Fragment", "Login failed");
                    editor.putString("ONLINE","false");
                    editor.apply();
                    msg = e.getMessage();
                }
                if (success == 1) {

                    userId = json.getString(TAG_MESSAGE);
                    domain = json.getString(TAG_DOMAIN);

                    if (domain.contains("ADMIN"))
                    {
                        // to rid of warning
                        //       i = new Intent(Login.this, AdminIntroduction.class);
                        i = new Intent(getActivity(), AndroidMainController.class);
                        startActivity(i);
                    }


                    editor.putString("userName", userName);
                    editor.putString("userId", userId);
                    editor.putString("domain", domain);
                    editor.putString("ONLINE", ONLINE);
                    editor.apply();
                    editor.commit();

                    Log.d("Login Fragment", "Login Ok with " + json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);
                } else {

                    Log.d("Login Fragment", "Login failed starting admin");
                    //	Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    //  i = new Intent(Login.this, AdminIntroduction.class);
                  //  getActivity().finish();
             //       startActivity(i);

                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

/*
                        Toast.makeText(getActivity(),
                              "Login OK",
                                Toast.LENGTH_LONG).show();
*/
/*

			Toast.makeText(getActivity(),
                    "Login \n userName = " + preferences.getString("userName","") +
                            "\n user_id = " +  preferences.getString("userId","") +
                            "\n domain = " + preferences.getString("domain","") +
                            "\n ONLINE = " + preferences.getString("ONLINE","") ,
                    Toast.LENGTH_LONG).show();
*/

            pDialog.dismiss();
        }
    }
}

//      Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();