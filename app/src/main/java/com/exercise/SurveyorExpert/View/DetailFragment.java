//package info.androidhive.tabsswipe;
//
///**
// * DetailFragment created by Calum Macaskill on 19/11/2014.
// */
//public class DetailFragment {
//}


package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
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

public class DetailFragment extends Fragment {
    View rootView;

    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;

    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

  //      Toast.makeText(getActivity(), "Changed to Detailed fragment", Toast.LENGTH_SHORT).show();

        createList();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            Log.d("EXPERT", "Login Succeeded getting arguments");
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


    void createList(){
        final ListView listView ;
        listView = (ListView) rootView.findViewById(R.id.detail_list);
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

                String itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getActivity(),
                        /*"Position :" + itemPosition +*/  "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }
        });
    }
}



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

