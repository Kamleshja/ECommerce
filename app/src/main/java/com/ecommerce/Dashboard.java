package com.ecommerce;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_navigation);
        navigationView = findViewById(R.id.navigation_view);

        toolbar.setTitle("E-Commerce");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (id){
                    case R.id.nav_home:
                        repalceFragment(new HomeFragment());
                        break;
                    case R.id.nav_settings:
                        repalceFragment(new MenFragment());
                        break;
                    case R.id.nav_logout:
                        repalceFragment(new WomanFragment());
                        break;
                    case R.id.nav_share:
                        Toast.makeText(Dashboard.this,"Share selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_rate:
                        Toast.makeText(Dashboard.this,"Rate selected",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });




    }

    private void repalceFragment(Fragment fragment ){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoyut,fragment);
        fragmentTransaction.commit();

    }
}