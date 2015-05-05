//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class NBCCodeFragment {
//}

package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class NBCCodeFragment extends Fragment {

// Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;

    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_nbc, container, false);
     //   Toast.makeText(getActivity(), "Changed to fragment_NBC", Toast.LENGTH_SHORT).show();
        createList();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "NBC Fragment Visible ", Toast.LENGTH_LONG).show();
        }
    }


    void createList(){
        final ListView listView ;
        listView = (ListView) rootView.findViewById(R.id.nbc_list);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
               // int itemPosition     = position;

                // ListView Clicked item value
                String itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
            //    Toast.makeText(getActivity(),
            //            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
             //           .show();

            }


        });
    }

}