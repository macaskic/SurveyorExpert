//package info.androidhive.tabsswipe;
//
///**
// * Created by calum on 19/11/2014.
// */
//public class ReportFragment {
//}



package com.exercise.SurveyorExpert.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;

public class ReportFragment extends Fragment implements View.OnClickListener {
    View rootView = null;
    private Bundle bundle = null;
    private String userId, userName, domain, ONLINE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   //     View rootView = inflater.inflate(R.layout.fragment_report, container, false);
   //     return rootView;
    //    Toast.makeText(getActivity(), "Changed to fragment_report", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_report, container, false);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "Report Fragment Visible ", Toast.LENGTH_LONG).show();
        }
    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:
                TextView test=(TextView)getView().findViewById(R.id.textView);
                test.setText("Hello");
                Toast.makeText(getActivity().getApplicationContext(), "Message : ", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

       // String url = "";
        EditText num=(EditText)getView().findViewById(R.id.EditText01);
     //   Toast.makeText(ReportFragment.this, "Message : " , Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity().getApplicationContext(), "Message : ", Toast.LENGTH_SHORT).show();

        String number = "tel:" + num.getText().toString().trim();
         Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
         startActivity(callIntent);
    }

}