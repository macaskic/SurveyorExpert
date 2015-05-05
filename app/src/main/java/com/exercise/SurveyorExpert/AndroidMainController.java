package com.exercise.SurveyorExpert;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import android.widget.Toast;

import com.exercise.AndroidViewPager.R;
import com.exercise.SurveyorExpert.View.CommentFragment;
import com.exercise.SurveyorExpert.View.ContextFragment;
import com.exercise.SurveyorExpert.View.DetailFragment;
import com.exercise.SurveyorExpert.View.EstimateFragment;
import com.exercise.SurveyorExpert.View.NBCCodeFragment;
import com.exercise.SurveyorExpert.View.PhotoFragment;
import com.exercise.SurveyorExpert.View.ProjectFragment;
import com.exercise.SurveyorExpert.View.ReportFragment;

import java.util.ArrayList;

public class AndroidMainController extends FragmentActivity /*TabActivity*/ implements TabHost.OnTabChangeListener {

    private String testString = null;

    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
  // private NewTabsAdapter mNewTabsAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TabHost mTabHost = null;

/*
    public NewTabsAdapter getNewTabsAdapter(){
        return mNewTabsAdapter;
    }

*/

     public void aSetTest(String arg){
        testString = arg;
     }
    public String aGetTest(){
        return testString;
    }


    /** Called when the activity is first created. */
    @Override
    @SuppressWarnings({"NullableProblems"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.pager);
        setContentView(mViewPager);


        final ActionBar bar = getActionBar();

        try {
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mTabsAdapter = new TabsAdapter(this, mViewPager);
  //       mNewTabsAdapter = new NewTabsAdapter(this, mViewPager);


        //    mTabsAdapter.addTab(bar.newTab().setText("Welcome"),
    //            MyFragmentB.class, null);
         mTabsAdapter.addTab(bar.newTab().setText("Project"),
                ProjectFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Context"),
                ContextFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Detail"),
                DetailFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("NBC"),
                NBCCodeFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Est"),
                EstimateFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Picture"),
                PhotoFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Com"),
                CommentFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Rep"),
                ReportFragment.class, null);
//        mTabsAdapter.addTab(bar.newTab().setText("Login"),
//                LoginFragment.class, null);
 //       mTabsAdapter.addTab(bar.newTab().setText("Register"),
 //               MyFragmentC.class, null);
 //       mTabsAdapter.addTab(bar.newTab().setText("Project"),
 //               ProjectFragment.class, null);



  //      mNewTabsAdapter.addTab(bar.newTab().setText("Login"),
  //              LoginFragment.class, null);
        /*
        mNewTabsAdapter.addTab(bar.newTab().setText("Project"),
                ProjectFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Context"),
                ContextFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Detail"),
                DetailFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("NBC"),
                NBCCodeFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Est"),
                WorkScheduleFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Picture"),
                PhotoFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Com"),
                CommentFragment.class, null);
        mNewTabsAdapter.addTab(bar.newTab().setText("Rep"),
                ReportFragment.class, null);
    //    mNewTabsAdapter.addTab(bar.newTab().setText("Login"),
    //            LoginFragment.class, null);
*/
   //     mTabHost.setOnTabChangedListener(this);
        mTabHost = new TabHost(this);

        if (mTabHost != null) {
            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                //    Toast.makeText(getApplicationContext(), "onTabChanged" + tabId, Toast.LENGTH_LONG).show();
                /*
                if(TAB_1_TAG.equals(tabId)) {
                    //destroy earth
                }
                if(TAB_2_TAG.equals(tabId)) {
                    //destroy mars
                }*/
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Null Tab Host", Toast.LENGTH_LONG).show();
        }

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }


    public void onTabChanged(String tabId) {
    //    Toast.makeText(getApplicationContext(), "Main Activity Selected Tab "+tabId, Toast.LENGTH_LONG).show();
      //  Log.i("selected tab index", "Current index - "+ mTabHost.getCurrentTab());
    }

    public void onResume() {
        super.onResume();
      //  Toast.makeText(this, "Main Activity on Resume", Toast.LENGTH_SHORT).show();
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

    public void switchFragment(int target){
     //   Toast.makeText(getApplicationContext(), "Switch Tab "+target, Toast.LENGTH_LONG).show();
        mViewPager.setCurrentItem(target);
      //  mViewPager.
    }



	public /*static*/ class TabsAdapter extends FragmentPagerAdapter
		implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
		
		private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        
       /* static*/ final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

		public TabsAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
            mContext = activity;
            mActionBar = activity.getActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

		public void addTab(Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

		@Override
		public void onPageScrollStateChanged(int state) {
			// Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			//  Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			//  Auto-generated method stub
      //      Toast.makeText(AndroidMainActivity.this, "Main Activity onPageSelected "
       //             + Integer.toString(position),
       //             Toast.LENGTH_SHORT).show();
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			//  Auto-generated method stub
			
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

	}


}