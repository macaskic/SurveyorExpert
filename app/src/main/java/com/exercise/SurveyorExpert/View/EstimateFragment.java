package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class EstimateFragment extends Fragment {
    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;
    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  View rootView = inflater.inflate(R.layout.fragment_schedule_of_works, container, false);
      //  return rootView;
        return  inflater.inflate(R.layout.fragment_schedule_of_works, container, false);


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "Estimate Fragment Visible ", Toast.LENGTH_LONG).show();
        }
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
}