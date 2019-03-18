package edu.freshfutures.seeka;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by tokmang on 7/1/2016.
 */
public class CustomProfileAdpater extends FragmentPagerAdapter {
    public CustomProfileAdpater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
