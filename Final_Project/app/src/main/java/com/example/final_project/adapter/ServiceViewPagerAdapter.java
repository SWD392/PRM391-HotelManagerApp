package com.example.final_project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.final_project.fragment.BillServiceFragment;
import com.example.final_project.fragment.TypeServiceFragment;

public class ServiceViewPagerAdapter extends FragmentStatePagerAdapter {

    public ServiceViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BillServiceFragment();
            case 1:
                return new TypeServiceFragment();
            default:
                return new BillServiceFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Bill Service";
                break;
            case 1:
                title = "Type Service";
                break;

        }
        return title;
    }
}
