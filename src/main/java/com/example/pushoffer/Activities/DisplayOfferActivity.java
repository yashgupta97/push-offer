package com.example.pushoffer.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pushoffer.Fragments.CollectionFragment;
import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.R;

import java.util.ArrayList;

public class DisplayOfferActivity extends AppCompatActivity {

    private ArrayList<Offer> offerArrayList1;
    private ArrayList<Offer> offerArrayList2;
    private ArrayList<Offer> offerArrayList3;
    SimpleFragmentPagerAdapter adapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_offer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        offerArrayList1 = PushOffer.getAllOffers("1");
        offerArrayList2 = PushOffer.getAllOffers("2");
        offerArrayList3 = PushOffer.getAllOffers("3");

        ViewPager viewPager = findViewById(R.id.viewpager);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return CollectionFragment.newInstance(offerArrayList1);
            } else if (position == 1) {
                return CollectionFragment.newInstance(offerArrayList2);
            } else {
                return CollectionFragment.newInstance(offerArrayList3);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TAB 1";
                case 1:
                    return "TAB 2";
                case 2:
                    return "TAB 3";
                default:
                    return null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.menu_clear) {
            tabLayout.getSelectedTabPosition();
//            PushOffer.deleteAllOffers();
//            offerArrayList.clear();
//            offerListAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
