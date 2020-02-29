package com.example.cpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView btmNav ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btmNav=findViewById(R.id.bottom_nav);
        Fragment currentFragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,currentFragment).commit();


        btmNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment currentFragment=null;

                    switch (item.getItemId()){
                        case R.id.action_home:
                            currentFragment=new HomeFragment();
                            break;

                        case R.id.action_more:
                            currentFragment=new MoreFragment();
                            break;

                        case R.id.action_Season:
                            currentFragment=new LeagueFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,currentFragment).commit();
                    return true;
                }
            };

}
