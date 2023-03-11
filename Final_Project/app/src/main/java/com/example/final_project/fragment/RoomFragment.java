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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.room_fragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.id_tablayout);
        mViewPager = view.findViewById(R.id.id_viewpager);
        RoomViewPagerAdapter roomViewPagerAdapter = new RoomViewPagerAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(roomViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

}
