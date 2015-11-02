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

        rootView =  inflater.inflate(R.layout.fragment_report, container, false);

        // need to set content view
        // setup button
        mTestMe = (Button) rootView.findViewById(R.id.button01);
        // register listeners
        mTestMe.setOnClickListener(this);
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
    }
}