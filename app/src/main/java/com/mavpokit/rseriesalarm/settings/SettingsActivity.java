package com.mavpokit.rseriesalarm.settings;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.settings.fragments.SetupOtherFragment;
import com.mavpokit.rseriesalarm.settings.fragments.SetupDelaysFragment;
import com.mavpokit.rseriesalarm.settings.fragments.SetupNumbersFragment;
import com.mavpokit.rseriesalarm.settings.fragments.SetupPasswordFragment;
import com.mavpokit.rseriesalarm.settings.fragments.SetupSirenFragment;
import com.mavpokit.rseriesalarm.settings.fragments.SetupZonesFragment;

import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    AlarmObject alarmObject;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        alarmObject = (AlarmObject) getIntent().getSerializableExtra(Consts.ALARM_OBJECT);

        setupToolbar();

        setupTabs();

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setLogo(toolbar);
        getSupportActionBar().setTitle(alarmObject.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setLogo(Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setLogo(R.drawable.ic_launcher);
        else
            toolbar.setLogo(R.mipmap.ic_launcher);
    }

    private void setupTabs() {
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(getPagerAdapter());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.WHITE,Color.YELLOW);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.indicator));

        int[]TAB_IMAGES = new int[]{
                R.drawable.tab_icon_password,
                R.drawable.tab_icon_numbers,
                R.drawable.tab_icon_zones,
                R.drawable.tab_icon_delay,
                R.drawable.tab_icon_siren,
                R.drawable.tab_icon_other};

        final String[] TAB_NAMES = new String[]{
                "Setup password",
                "Setup numbers",
                "Setup zones",
                "Setup delays",
                "Setup siren",
                "Miscellaneous" };

        //set icons and color them with inactivetab color
        for (int i=0;i<tabLayout.getTabCount();i++){
            tabLayout.getTabAt(i).setIcon(TAB_IMAGES[i]);
            tabLayout.getTabAt(i).getIcon().setColorFilter(getResources().getColor(R.color.inactiveTab), PorterDuff.Mode.SRC_IN);
        }

        //mark first tab icon with color at startup
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
        getSupportActionBar().setTitle(TAB_NAMES[0]);

        //for toolabar title change when scrolling tabs
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(SettingsActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(TAB_NAMES[position]);
            }
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}
        });

        //for tab icon color change
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                setTitle(tab.getText()); //comment if tab has only icon
                tab.getIcon().setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.inactiveTab), PorterDuff.Mode.SRC_IN);
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private FragmentPagerAdapter getPagerAdapter() {
        return new FragmentPagerAdapter(getSupportFragmentManager()) {

            private Fragment[] fragments = new Fragment[]{
                    SetupPasswordFragment.newInstance(alarmObject.getNumber()),
                    SetupNumbersFragment.newInstance(alarmObject.getNumber()),
                    SetupZonesFragment.newInstance(alarmObject.getNumber()),
                    SetupDelaysFragment.newInstance(alarmObject.getNumber()),
                    SetupSirenFragment.newInstance(alarmObject.getNumber()),
                    SetupOtherFragment.newInstance(alarmObject.getNumber())};


            private static final String TAG = "-----PagerAdapter-----";

            final int PAGE_COUNT = 6;
//            private String[] TAB_NAMES = new String[]{
//                    "Animated\nmarkers",
//                    "Window\nTransitions",
//                    "Transitions\nFramework",
//                    "Property\nanimation",
//                    "Layout\nTransitions",
//                    "Gif\nanimation"};

            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, "getItem " + position);
                return fragments[position];
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

//            @Override
//            public CharSequence getPageTitle(int position) {
//                return TAB_NAMES[position];
//                Drawable image = ContextCompat.getDrawable(MainActivity.this, TAB_IMAGES[position]);
//                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//                SpannableString sb = new SpannableString("AA ");
//                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                return sb;
//            }

        };
    }


}
