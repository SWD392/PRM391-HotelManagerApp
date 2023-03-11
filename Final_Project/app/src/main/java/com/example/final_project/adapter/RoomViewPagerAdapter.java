package com.example.final_project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.final_project.fragment.BillServiceFragment;
import com.example.final_project.fragment.RoomFragment;
import com.example.final_project.fragment.RoomListFragment;
import com.example.final_project.fragment.RoomTypeFragment;
import com.example.final_project.fragment.TypeServiceFragment;

public class RoomViewPagerAdapter extends FragmentStatePagerAdapter {

    public RoomViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RoomListFragment();
            case 1:
                return new RoomTypeFragment();
            default:
                return new RoomListFragment();
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
                title = "Room";
                break;
            case 1:
                title = "Room Type";
                break;

        }
        return title;
    }
}
