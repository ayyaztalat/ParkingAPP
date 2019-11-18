package com.example.parkingapp.Activities.Fragment_parking_info_insider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.parkingapp.Fragments.PaymentFragment;
import com.example.parkingapp.Fragments.PersonalFragment;
import com.example.parkingapp.Fragments.SecurityFragment;

import java.util.Map;

public class TabsParking  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsParking(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                Parking_info_fragment contact = new Parking_info_fragment();
                return contact;

            case 1:
                Photos_parking about = new Photos_parking();
                return about;
            case 2:
                Map_fragment_parking personal = new Map_fragment_parking();
                return personal;
            default:
                return null;
        }
    }
}
