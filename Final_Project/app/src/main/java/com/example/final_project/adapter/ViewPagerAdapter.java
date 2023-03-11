package com.example.final_project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.final_project.fragment.BillFragment;
import com.example.final_project.fragment.CustomerFragment;
import com.example.final_project.fragment.ServiceFragment;
import com.example.final_project.fragment.RoomFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                return new BillFragment();
            case 1:
                return new ServiceFragment();
            case 2:
                return new RoomFragment();
            case 3:
                return new CustomerFragment();


        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
