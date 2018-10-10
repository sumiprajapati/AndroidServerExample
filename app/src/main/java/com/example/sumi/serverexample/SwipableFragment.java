package com.example.sumi.serverexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

public class SwipableFragment extends Fragment {
    PagerSlidingTabStrip slidingTabStrip;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.swipablefragment,null);
        slidingTabStrip=v.findViewById(R.id.pager_tabs);
        viewPager=v.findViewById(R.id.viewpager);
        FragmentManager fm=getChildFragmentManager();//fragment bata  child fragment banauna define gareko
        viewPager.setAdapter(new MyAdapter(getActivity(),fm));
        slidingTabStrip.setViewPager(viewPager);//pagertab ra viewpager jodiyo


        return v;
    }
}
