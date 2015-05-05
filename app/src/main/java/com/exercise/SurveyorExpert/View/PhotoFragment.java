//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class PhotoFragment {
//}

package com.exercise.SurveyorExpert.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class PhotoFragment extends Fragment {

    // Share variables between fragments
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor spEditor = null;
    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    //    View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
    //    return rootView;
        return inflater.inflate(R.layout.fragment_photo, container, false);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "Photo Fragment Visible ", Toast.LENGTH_LONG).show();
        }
    }
}