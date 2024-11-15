package edu.utsa.cs3773.pathseer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

//NavigationActivity that provides common navigation drawer functionality for all screens
public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;  //Drawer layout that holds the navigation view
    NavigationView navigationView;  //Navigation view to display menu items
    Toolbar toolbar;  //Toolbar for the action bar at the top of the screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation); //Set the common layout for the base activity

        //Initialize DrawerLayout, NavigationView, and Toolbar components
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //Set up the Toolbar to work with the action bar
        setSupportActionBar(toolbar);

        //Set up the toggle for opening and closing the navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();  //Sync the toggle state

        //Set the current class as the listener for navigation item selection
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Handles navigation item selection from the drawer menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("NavigationActivity", "Menu item clicked: " + menuItem.getTitle());
        //Check which menu item was clicked and start the appropriate activity
        if (menuItem.getItemId() == R.id.nav_home) {
            startActivity(new Intent(this, HomeScreen.class));  //Navigate to HomeScreen
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsScreen.class));  //Navigate to SettingsScreen
        } else if (menuItem.getItemId() == R.id.nav_post_job) {
            startActivity(new Intent(this, PostJobScreen.class));  //Navigate to PostJobScreen
        } else if (menuItem.getItemId() == R.id.nav_search_job) {
            startActivity(new Intent(this, JobSearchScreen.class));  //Navigate to JobSearchScreen
        }else if (menuItem.getItemId() == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileScreen.class));  //Navigate to ProfileScreen
        } else if (menuItem.getItemId() == R.id.nav_sign_out) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear back stack
            startActivity(intent);  //Navigate to MainActivity (Sign Out)
            finish();  //Finish the current activity
        }

        //Close the navigation drawer with a slight delay and uncheck all menu items
        drawerLayout.postDelayed(() -> {
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
            uncheckAllMenuItems(navigationView.getMenu());  // Clear all selections
        }, 200);

        return true;
    }

    //Helper method to uncheck all menu items, including items in nested submenus
    private void uncheckAllMenuItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);  //Get each menu item
            item.setChecked(false);  //Uncheck the item
            if (item.hasSubMenu()) {  //If the item has a submenu
                uncheckAllMenuItems(item.getSubMenu());  //Recursively uncheck items in the submenu
            }
        }
    }
}
