package com.example.final_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.final_project.R;
import com.example.final_project.adapter.RoomViewPagerAdapter;
import com.example.final_project.adapter.ServiceViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class RoomFragment extends Fragment {
TabLayout mTabLayout;
ViewPager mViewPager;

View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.room_fragment, container, false);
        mTabLayout = mView.findViewById(R.id.id_tablayout);
        mViewPager = mView.findViewById(R.id.id_viewpager);
        RoomViewPagerAdapter roomViewPagerAdapter = new RoomViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(roomViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return mView;
    }



}
