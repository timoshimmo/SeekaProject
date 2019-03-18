package edu.freshfutures.seeka.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.freshfutures.seeka.PurchaseCreditsFragment;
import edu.freshfutures.seeka.UsedCreditFragment;

/**
 * Created by tokmang on 9/2/2016.
 */
public class PurHistoryAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PurHistoryAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                PurchaseCreditsFragment tab1 = new PurchaseCreditsFragment();
                return tab1;
            case 1:
                UsedCreditFragment tab2 = new UsedCreditFragment();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
