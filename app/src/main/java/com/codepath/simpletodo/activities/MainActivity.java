package com.codepath.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.adapters.TodoFragmentsPagerAdapter;
import com.codepath.simpletodo.fragments.TodoFragment;

public class MainActivity extends AppCompatActivity implements TodoFragment.OnTodoItemStateChanged{

    private final int ADD_ITEM_REQUEST_CODE = 10;
    private ViewPager mViewPager;
    private TodoFragmentsPagerAdapter mPagerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new TodoFragmentsPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);
        mViewPager.setAdapter(mPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_ITEM_REQUEST_CODE) {
            onTodoItemStateChanged();
        }
    }

    public void onAddNewTask(MenuItem mi) {
        Intent i = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(i,ADD_ITEM_REQUEST_CODE);
    }

    public void onTodoItemStateChanged() {
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
