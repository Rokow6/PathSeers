package edu.utsa.cs3773.pathseer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);

        //Initialize menu items
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //Toolbar
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    //Navigation for menu items on click
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_home) {
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(HomeScreen.this, SettingsScreen.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(HomeScreen.this, ProfileScreen.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_sign_out) {
            Intent intent = new Intent(HomeScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        //Close drawer with a delay and uncheck items after closing
        drawerLayout.postDelayed(() -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            uncheckAllMenuItems(navigationView.getMenu());
        }, 200);
        return true;
    }

    //Helper method to uncheck all items, including those in nested menus
    private void uncheckAllMenuItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(false);
            if (item.hasSubMenu()) {
                // Uncheck items in the submenu as well
                uncheckAllMenuItems(item.getSubMenu());
            }
        }
    }
}