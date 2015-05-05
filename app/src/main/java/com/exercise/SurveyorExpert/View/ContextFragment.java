package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;
import com.exercise.SurveyorExpert.Model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContextFragment extends Fragment /* implements  View.OnClickListener, TabHost.OnTabChangeListener*/  {

    // Share variables between fragments
    private  SharedPreferences preferences = null;
    private  SharedPreferences.Editor spEditor = null;

    ListView listView ;
    private String resource, project, userName;
    private String retMessage, userId, domain, itemValue, mbcode, description;
    private String childPos, parentPos, nbcMarker, nbcDescription, ONLINE, section, element;
    private int success = 0;
    private String[] mbcodes = null;
    private String[] descriptions = null;
    private String[] values = null;

    ArrayAdapter<String> adapter = null;

    private static final String READ_COMPONENT_URL
         //   = "http://www.surveyorexpert.com/webservice/getComponent2.php";
            = "http://www.surveyorexpert.com/webservice/getElement2.php";

    // JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_ELEMENT = "element";
    private static final String TAG_ELEMENT_ID = "element_id";
    private static final String TAG_SECTION = "section";
    private static final String TAG_SECTION_ID = "section_id";
    private static final String TAG_MBCODE= "mbcode";
    private static final String TAG_DESCRIPTION = "description";

    private JSONObject json  = null;
    private List<NameValuePair> jsonParams = null;
    private JSONParser jsonParser = new JSONParser();

    private JSONArray mComponent = null;
    private ArrayList<HashMap<String, String>> mComponentList;

    private HashMap<String, String> mOut = null;
    List<String> elemList = new ArrayList<String>();
    List<String> sectList = new ArrayList<String>();
    List<String> compList = new ArrayList<String>();

    HashMap<String, String> mapComponentIndex = new HashMap<String, String>();
    private String state;
    private View rootView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  View
        rootView = inflater.inflate(R.layout.fragment_context, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        spEditor = preferences.edit();
        if (spEditor == null || preferences == null){
            Log.d("EXPERT", "Shared Preference Failure");
            Toast.makeText(getActivity(), "Oh No : "
                    + "FAILED" , Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Context Fragment", Toast.LENGTH_SHORT).show();
            Log.d("EXPERT", " Shared Preference is ok");
            new GetDomainData().execute();
        }

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            Log.d("EXPERT", "Project Succeeded getting arguments");
            //      Toast.makeText(getActivity(), "Context Fragment Preview", Toast.LENGTH_SHORT).show();
            //      preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            //     spEditor = preferences.edit();


            if (spEditor == null || preferences == null){
                Log.d("EXPERT", "Shared Preference Failure");
                Toast.makeText(getActivity(), "Oh No : "
                        + "FAILED" , Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity(), "Context Fragment", Toast.LENGTH_SHORT).show();
                Log.d("EXPERT", "In Context Hint - Shared Preference is ok");

                try {
                    userId = preferences.getString("userId","Default").toString();
                    userName = preferences.getString("userName","Default").toString();
                    domain = preferences.getString("domain","Default").toString();
                    ONLINE = preferences.getString("ONLINE","Default").toString();
                    resource = preferences.getString("resource", "Default").toString();
                    project =  preferences.getString("project", "Default").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), "OK : "
                             + userId + " " + userName + " "  + resource + " " + project, Toast.LENGTH_LONG).show();

            }
        }
    }

/*
    @Override
    public void onClick(View arg0) {
        // not used
    }
    @Override
    public void onTabChanged(String tabId) {
      //  Toast.makeText(getActivity(), "Context Fragment Selected Tab "+tabId, Toast.LENGTH_LONG).show();
        //  Log.i("selected tab index", "Current index - "+ mTabHost.getCurrentTab());
    }
*/


    /************************************* Get DATA  *********************************/
    class GetDomainData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*
            bundle = getActivity().getIntent().getExtras();
            userName =  bundle.getString("userName") ;
            userId =  bundle.getString("userId") ;
            domain = bundle.getString("domain") ;
            ONLINE =  bundle.getString("ONLINE") ;
            */

      //      Toast.makeText(getActivity(), "Changed to Context onPreExecute", Toast.LENGTH_SHORT).show();
/*
            Toast.makeText(getActivity(),
                    "Context onPreExecute \n userName = " + userName +
                            "\n userId = " +  userId +
                            "\n domain = " + domain +
                            "\n ONLINE = " + ONLINE,
                    Toast.LENGTH_LONG).show();
*/

			/*
			Toast.makeText(getApplicationContext(),
					"GetDomainData  onPreExecute \nDomain = " + domain
					+ "\n user_id = " + user_id
					+ "\n parentPos = " + parentPos
					+ "\n childPos = " + childPos,
					Toast.LENGTH_LONG).show();
					*/
        }

        @Override
        protected String doInBackground(String... args) {

            json = new JSONObject();
            jsonParams = new ArrayList<NameValuePair>();
            jsonParams.add(new BasicNameValuePair("domain", domain));
 /*           jsonParams.add(new BasicNameValuePair("parentPos", parentPos));

            Log.d("Detail List View - do in back ground ",
                    "domain = " + domain + " parentPos:  " + parentPos);
            //
            */
            addItemsOnComponent();

            return null;
        }


        private void addItemsOnComponent() {

            try{
                json = jsonParser.makeHttpRequest(READ_COMPONENT_URL, "POST", jsonParams);
                success = json.getInt(TAG_SUCCESS);
                Log.d("DetailListView", "Component Good Call success = " + Integer.toString(success));
            } catch(Exception e){
                Log.d("DetailListView", "Component Exception Success = " + Integer.toString(success));
                e.printStackTrace();
            }


            if (success == 1) {
                descriptions = new String[] { "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "" };
                mbcodes = new String[] { "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "" };
                values = new String[] { "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "" };


                //	values = new String[20];
                mComponentList = new ArrayList<HashMap<String, String>>();
                try {

                    mComponent = json.getJSONArray(TAG_POSTS);

                    Log.d("DetailListView DATA", "Component got data size = " + Integer.toString(mComponent.length()));

                    for (int i = 0; i < mComponent.length(); i++) {
                        JSONObject c = mComponent.getJSONObject(i);
                        mbcodes[i] = c.getString(TAG_ELEMENT_ID);
                        descriptions[i] = c.getString(TAG_ELEMENT);
              //          values[i] = descriptions[i] + " NBC = " + mbcodes[i];
                    }
                    json.getString(TAG_SUCCESS);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        protected void onPostExecute(String file_url) {
        //    Toast.makeText(getActivity(), "Changed to Context onPostExecute", Toast.LENGTH_SHORT).show();
            createList();

/*
            Toast.makeText(getActivity(),
                    "Login \n userName = " + preferences.getString("userName","") +
                            "\n user_id = " +  preferences.getString("userId","") +
                            "\n domain = " + preferences.getString("domain","") +
                            "\n ONLINE = " + preferences.getString("ONLINE","") ,
                    Toast.LENGTH_LONG).show();
                    */
/*
            Toast.makeText(getActivity(),
                    "Login \n userName = "  + userName +
                            "\n user_id = " + userId +
                            "\n domain = " + domain +
                            "\n ONLINE = " + ONLINE ,
                    Toast.LENGTH_LONG).show();
*/



		//	Toast.makeText(getActivity(),
         //           "GetDomainData  onPost Execute \nDomain = ",
          //          Toast.LENGTH_SHORT).show();

        }

        void createList(){
            final ListView listView ;
            listView = (ListView) rootView.findViewById(R.id.list);
            String[] values = new String[] { "Android List View", "Adapter implementation", "Simple List View In Android",
                    "Create List View Android", "Android Example", "List View Source Code", "List View Array Adapter", "Android Example List View" };

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
            // simple_list_item_1 :  Android internal layout view
            // android.R.id.text1    :  In Android internal layout view already defined text fields to show data
            //  values                       :  User defined data array.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);


            // Assign adapter to ListView
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                   // int itemPosition     = position;

                    // ListView Clicked item value
                    String itemValue    = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getActivity(),
                           /* "Position :" + itemPosition + */ "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();

                }


            });
        }
    }
}