package com.film.rba.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.film.rba.R;
import com.film.rba.fragment.HomeFragment;
import com.film.rba.fragment.ProfileFragment;
import com.film.rba.fragment.SearchFragment;
import com.film.rba.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;


public class HomeScreen extends AppCompatActivity {
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    TextView textView5,tool_title;
    View view1,view2,view3,view4,view5;
    Toolbar toolbar;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);




        view1=findViewById(R.id.viewid_1);

        view2=findViewById(R.id.viewid_2);
        view3=findViewById(R.id.viewid_3);
        view4=findViewById(R.id.viewid_4);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:

                                switchToFragment2();

                            view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            break;
                       case R.id.navigation_sms:
                           switchToFragment1();

                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            break;
                       case R.id.event:
                           switchToFragment3();

                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            break;

                            case R.id.calender:
                                switchToFragment4();
                                view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            break;


                    }

                  /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                    return true;
                }
            };



    public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }

    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }


    public void switchToFragment4() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
    }
  /*  public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
    }

    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new EventFragment()).commit();
    }


    public void switchToFragment4() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
    }

    public void switchToFragment5() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new CalenderFragment()).commit();
    }


    public void switchToFragment6() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }


    public void switchToFragment7() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
    }
*/
   /* public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }*/

    }

