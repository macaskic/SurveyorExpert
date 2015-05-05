//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class CommentFragment {
//}


package com.exercise.SurveyorExpert.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class CommentFragment extends Fragment {
    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
      //  return rootView;

        return inflater.inflate(R.layout.fragment_comment, container, false);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "Comment Fragment Visible ", Toast.LENGTH_LONG).show();
        }
    }
}