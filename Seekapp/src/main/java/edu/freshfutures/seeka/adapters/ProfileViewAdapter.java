package edu.freshfutures.seeka.adapters;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import edu.freshfutures.seeka.FragmentProfileEdu;
import edu.freshfutures.seeka.FragmentProfileInfo;
import edu.freshfutures.seeka.FragmentProfileInterests;


/**
 * Created by tokmang on 4/28/2016.
 */
public class ProfileViewAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private Context context;

    public ProfileViewAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.context = ctx;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentProfileInfo tab1 = new FragmentProfileInfo();
                return tab1;
            case 1:
                FragmentProfileEdu tab2 = new FragmentProfileEdu();
                return tab2;
            case 2:
                FragmentProfileInterests tab3 = new FragmentProfileInterests();
                return tab3;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
