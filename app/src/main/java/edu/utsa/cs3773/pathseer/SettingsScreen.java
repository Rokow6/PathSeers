package edu.utsa.cs3773.pathseer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsScreen extends NavigationActivity {

    private TextView accountOption;
    private TextView notificationsOption;
    private TextView legalOption;
    private TextView supportOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        // Initialize views
        accountOption = findViewById(R.id.account_option);
        notificationsOption = findViewById(R.id.notifications_option);
        legalOption = findViewById(R.id.legal_option);
        supportOption = findViewById(R.id.support_option);

        // Set click listeners for each option
        accountOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Account settings screen
                Intent intent = new Intent(SettingsScreen.this, AccountSetting.class);
                startActivity(intent);
            }
        });

        // notificationsOption.setOnClickListener(new View.OnClickListener() {
        //@Override
        // public void onClick(View v) {
        // Navigate to Notifications settings screen
        //Intent intent = new Intent(SettingsScreen.this, NotificationsSetting.java);
        //startActivity(intent);
        // }
        // });

        //legalOption.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        // Navigate to Legal settings screen
        //Intent intent = new Intent(SettingsScreen.this, LegalSetting.java);
        // startActivity(intent);
        //  }
        // });

        //supportOption.setOnClickListener(new View.OnClickListener() {
        //@Override
        // public void onClick(View v) {
        // Navigate to Support settings screen
        //Intent intent = new Intent(SettingsScreen.this, SupportSetting.java);
        // startActivity(intent);
        //   }
        // });
        //  }
    }
}
