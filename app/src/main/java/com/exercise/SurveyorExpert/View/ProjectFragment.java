package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;
import com.exercise.SurveyorExpert.AndroidMainController;
import com.exercise.SurveyorExpert.Model.JSONParser;
import com.exercise.SurveyorExpert.Other.CustomOnItemSelectedListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ProjectFragment created by Calum Macaskill on 19/11/2014.
 */

public class ProjectFragment extends Fragment implements View.OnClickListener/*, TabHost.OnTabChangeListener */ {

    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;

    private Bitmap bitmap = null;
    //   private EditText user, pass;
    private String resource, project, company, logoName, msg;
    private TextView organisation;
    private ImageView logo;
    private Button enter;
    private Spinner Resource, Project;
    private String userId, userName, domain, ONLINE = "";
    private int success = 0;
    private static int count = 0;

    // JSON parser class
    private JSONParser jsonParser = new JSONParser();

    private JSONArray mResource = null;
    private ArrayList<HashMap<String, String>> mResourceList;

    private JSONArray mProject = null;
    private ArrayList<HashMap<String, String>> mProjectList;
    private ArrayList<HashMap<String, String>> mTmpList;

    private JSONObject json = null;
    private List<NameValuePair> jsonParams = null;

    private HashMap<String, String> mOut = null;

    //   private String state;

    private static final String GETRESOURCE_URL =
            "http://www.surveyorexpert.com/webservice/getResource.php";
    private static final String GETPROJECT_URL =
            "http://www.surveyorexpert.com/webservice/getProject.php";
    private static final String GETORGANISATION_URL =
            "http://www.surveyorexpert.com/webservice/getOrganisation.php";
    private static final String LOGO_URL =
            "http://www.surveyorexpert.com/webservice/logos/";

    // JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_SURNAME = "surname";
    private static final String TAG_PROJECT = "project_name";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_LOGO = "logo_name";

    private View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_project, container, false);

        enter = (Button) rootView.findViewById(R.id.bIntroEnter);
        enter.setOnClickListener(this);

        logo = (ImageView) rootView.findViewById(R.id.ivIntroLogo);

        if (spEditor == null || preferences == null){
            Log.d("SurveyorExpert", "Shared Preference Failure");
            Toast.makeText(getActivity(), "Oh No : "
                    + "FAILED" , Toast.LENGTH_LONG).show();
        }
        else {
       //     Toast.makeText(getActivity(), "Context Fragment", Toast.LENGTH_SHORT).show();
            Log.d("SurveyorExpert", " Shared Preference is ok");

            new GetResAndProj().execute();
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {


            Log.d("SurveyorExpert", "Login Succeeded getting arguments");
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            spEditor = preferences.edit();


            if (spEditor == null){
                Toast.makeText(getActivity(), "Oh No : "
                        + "FAILED" , Toast.LENGTH_LONG).show();
            }
            else{

                userId = preferences.getString("userId","Default").toString();
                userName = preferences.getString("userName","Default").toString();
                domain = preferences.getString("domain","Default").toString();
                ONLINE = preferences.getString("ONLINE","Default").toString();
            }
        }
    }



        @Override
    public void onClick(View arg0) {
            // NOT USED BUT REQUIRED
     //  Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
    }


    // add items into spinner dynamically
    public void addItemsOnProject() {

        Project = (Spinner) rootView.findViewById(R.id.spEditEstimate);
        List<String> list = new ArrayList<String>();

        //mProjectList. is a ArrayList of maps
        for (int i = 0; i < mProjectList.size(); i++) {
            mOut = mProjectList.get(i);
            for (String key: mOut.keySet()) {
                list.add( mOut.get(key));
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Project.setAdapter(dataAdapter);
    }

    public void addItemsOnResource() {

        Resource = (Spinner) rootView.findViewById(R.id.spEditSeverity);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < mTmpList.size(); i++) {
            mOut = mTmpList.get(i);
            for (String key: mOut.keySet()) {
                list.add( mOut.get(key));
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Resource.setAdapter(dataAdapter);

    }

    public void addListenerOnSpinnerItemSelection() {

        Resource = (Spinner) rootView.findViewById(R.id.spEditSeverity);
        Resource.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        Button enter = null;
        Resource = (Spinner) rootView.findViewById(R.id.spEditSeverity);
        Project = (Spinner) rootView.findViewById(R.id.spEditEstimate);
        enter = (Button) rootView.findViewById(R.id.bIntroEnter);
        organisation = (TextView) rootView.findViewById(R.id.tvOrganisationName);


        enter.setOnClickListener(this);
        //	btnSubmit = (Button) findViewById(R.id.bIntroEnter);

        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            //    bundle = getActivity().getIntent().getExtras();
                resource = String.valueOf(Resource.getSelectedItem());
                project = String.valueOf(Project.getSelectedItem());
                if (spEditor == null){
                    Toast.makeText(getActivity(), "Oh No : "
                            + "FAILED" , Toast.LENGTH_LONG).show();
                }
                else {
                    spEditor.putString("resource", resource);
                    spEditor.putString("project", project);
                    spEditor.apply();
                }

                // Switch to ContextFragment
               ((AndroidMainController)getActivity()).switchFragment(1);

            }
        });
    }



    class GetResAndProj extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("SurveyorExpert", "Try creating ProjectFragment................");
/*
            userId = preferences.getString("userId", "");
            userName = preferences.getString("userName", "");
            domain = preferences.getString("domain", "");
            ONLINE = preferences.getString("ONLINE", "");

            Toast.makeText(getActivity(), "Got Project Activity : " + ONLINE
                    + "\n userName: " + userName
                    + "\n userId: " + userId
                    + "\n domain: " + domain
                    , Toast.LENGTH_SHORT).show();
*/

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                json = new JSONObject();
                jsonParams = new ArrayList<NameValuePair>();
                jsonParams.add(new BasicNameValuePair("userId", userId));

                populateResourceList();
                populateProjectList();
                populateOrganisation();
                populateLogo();
            } catch (Exception e) {
               // e.printStackTrace();
            }

            return null;
        }



        protected void onPostExecute(String file_url) {
          //  Toast.makeText(getActivity(), "msg = " + msg, Toast.LENGTH_SHORT).show();
            //     Toast.makeText(getActivity(), "Project Fragment onPostExecute called ", Toast.LENGTH_SHORT).show();

            addItemsOnResource();
            addItemsOnProject();

            addListenerOnButton();
            addListenerOnSpinnerItemSelection();
            organisation.setText(company);
            logo.setImageBitmap(bitmap);

         //   Toast.makeText(getActivity(), "Post execute done ", Toast.LENGTH_SHORT).show();
        }

        private void populateLogo() {

            String imageURL = LOGO_URL + logoName;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        public void populateOrganisation() {

            try {
                json = jsonParser.makeHttpRequest(GETORGANISATION_URL, "POST", jsonParams);
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (success == 1) {
                mResourceList = new ArrayList<HashMap<String, String>>();
                try {
                    mResource = json.getJSONArray(TAG_POSTS);
                    for (int i = 0; i < mResource.length(); i++) {

                        JSONObject c = mResource.getJSONObject(i);

                        String userId = c.getString(TAG_USER_ID);
                        String comp = c.getString(TAG_COMPANY);
                        String lName = c.getString(TAG_LOGO);

                        company = comp;
                        logoName = lName;
                    }
                    json.getString(TAG_MESSAGE);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        private void populateResourceList() {

            try {
                json = jsonParser.makeHttpRequest(GETRESOURCE_URL, "POST", jsonParams);
                success = json.getInt(TAG_SUCCESS);
                msg = "populateResourceList success: = " + Integer.toString(success);
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                //        Toast.makeText(getActivity(), "populateResourceList: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (success == 1) {
                mResourceList = new ArrayList<HashMap<String, String>>();
                try {
                    mResource = json.getJSONArray(TAG_POSTS);
                    for (int i = 0; i < mResource.length(); i++) {
                        JSONObject c = mResource.getJSONObject(i);

                        String userId = c.getString(TAG_USER_ID);
                        String surname = c.getString(TAG_SURNAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(userId, surname);

                        // adding HashList to ArrayList
                        mResourceList.add(map);
                    }

                    json.getString(TAG_MESSAGE);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                mTmpList = mResourceList;
                //	Log.d("Introduction", "populateResourceList OK " + Integer.toString(mResourceList.size()));
            }

        }

        private void populateProjectList() {

            try {
                json = jsonParser.makeHttpRequest(GETPROJECT_URL, "POST", jsonParams);
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (success == 1) {

                mProjectList = new ArrayList<HashMap<String, String>>();
                try {
                    mProject = json.getJSONArray(TAG_POSTS);
                    for (int i = 0; i < mProject.length(); i++) {
                        JSONObject c = mProject.getJSONObject(i);

                        String userId = c.getString(TAG_USER_ID);
                        String project_name = c.getString(TAG_PROJECT);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_USER_ID, project_name);

                        // adding HashList to ArrayList
                        mProjectList.add(map);
                    }

                    json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
//      Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
