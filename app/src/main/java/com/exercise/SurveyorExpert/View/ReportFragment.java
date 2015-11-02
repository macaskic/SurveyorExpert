//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class ReportFragment {
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
import android.widget.Button;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class ReportFragment extends Fragment implements View.OnClickListener {

    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;


    private Button  mTestMe;
    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set content view
    //    getActivity().setContentView(R.layout.fragment_report);
        // setup buttons
    //    mTestMe = (Button) getActivity().findViewById(R.id.button01);
        // register listeners
     //   mTestMe.setOnClickListener(this);

   //     View rootView = inflater.inflate(R.layout.fragment_report, container, false);
   //     return rootView;
    //    Toast.makeText(getActivity(), "Changed to fragment_report", Toast.LENGTH_SHORT).show();
        rootView =  inflater.inflate(R.layout.fragment_report, container, false);
       // getActivity().setContentView(R.layout.fragment_report);
        mTestMe = (Button) rootView.findViewById(R.id.button01);
        mTestMe.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "Test Report Fragment Visible ", Toast.LENGTH_SHORT).show();
        }
        if (isVisibleToUser) {

            // Set content view
           //     getActivity().setContentView(R.layout.fragment_report);
            // setup buttons
            //    mTestMe = (Button) getActivity().findViewById(R.id.button01);
            // register listeners
            //   mTestMe.setOnClickListener(this)

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


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button01:
             //   TextView test=(TextView)getView().findViewById(R.id.textView);
             //   test.setText("Hello");
             //   Toast.makeText(getActivity().getApplicationContext(), "Message : ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Button Pressed ", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
/*
       // String url = "";
        EditText num=(EditText)getView().findViewById(R.id.EditText01);
     //   Toast.makeText(ReportFragment.this, "Message : " , Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity().getApplicationContext(), "Message : This is it", Toast.LENGTH_SHORT).show();

        String number = "tel:" + num.getText().toString().trim();
         Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
         startActivity(callIntent);
         */
    }

}