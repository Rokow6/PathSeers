package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    public static void saveUserSession(Context context, int userId, String fullName, String accountType) {
        SharedPreferences sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_id", userId);
        editor.putString("fullName", fullName);
        editor.putString("accountType", accountType);
        editor.apply();
    }
}
