package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.simpletodo.fragments.DoneFragment;
import com.codepath.simpletodo.fragments.TodoFragment;

/**
 * Created by rpraveen on 6/25/16.
 */
public class TodoFragmentsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String [] tabTitles = new String[] {"Todo", "Done"};
    private Context context;

    public TodoFragmentsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TodoFragment.newInstance(position);
            case 1:
            case 2:
                return DoneFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
