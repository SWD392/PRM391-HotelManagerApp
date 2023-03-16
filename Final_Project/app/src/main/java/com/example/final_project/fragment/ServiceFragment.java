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
import com.example.final_project.adapter.ServiceViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ServiceFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.service_fragment, container, false);

        mTabLayout = mView.findViewById(R.id.id_tablayout_service);
        mViewPager = mView.findViewById(R.id.id_viewpager_service);
        ServiceViewPagerAdapter serviceViewPagerAdapter = new ServiceViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(serviceViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return mView;
    }

}
