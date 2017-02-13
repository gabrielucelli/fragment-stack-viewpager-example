package com.gabrielucelli.examples.stackfragmentviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Stack<Integer> pageStack;
    private CustomPagerAdapter customPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pageStack = new Stack<>();
        pageStack.add(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addToPageStack(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {
            if (pageStack.size() <= 1)
                super.onBackPressed();
            else
                onBackPageStack();
        }
    }

    public void addToPageStack(int page) {

        int position = -1;

        for (int i = 0; i < pageStack.size(); i++)
            if (pageStack.get(i) == page)
                position = i;

        if (position > -1)
            pageStack.remove(position);

        pageStack.add(page);
    }

    public void onBackPageStack() {
        popFromPageStack();
        backToLastPageOfStack();
    }

    public int popFromPageStack() {
        return pageStack.pop();
    }

    public void backToLastPageOfStack() {
        viewPager.setCurrentItem(pageStack.peek());
    }

    public void openNewContentFragment(int depth, int fontSize) {
        HostFragment hostFragment = (HostFragment) customPagerAdapter.getItem(viewPager.getCurrentItem());
        hostFragment.replaceFragment(ContentFragment.newInstance(depth, fontSize), true);
    }

}
