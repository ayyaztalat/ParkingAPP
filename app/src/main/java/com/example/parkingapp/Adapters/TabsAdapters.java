package com.example.parkingapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.parkingapp.Fragments.PaymentFragment;
import com.example.parkingapp.Fragments.PersonalFragment;
import com.example.parkingapp.Fragments.SecurityFragment;

public class TabsAdapters extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapters(FragmentManager fm, int NoofTabs){
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
                PersonalFragment personal = new PersonalFragment();
                return personal;
            case 1:
                SecurityFragment about = new SecurityFragment();
                return about;
            case 2:
                PaymentFragment contact = new PaymentFragment();
                return contact;
            default:
                return null;
        }
    }
}
