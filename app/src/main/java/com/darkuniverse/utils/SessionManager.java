package com.darkuniverse.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "DarkUniverseSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_EXPIRED = "expired";
    private static final String KEY_LOGGED_IN = "loggedIn";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void login(String username, String role, String expired) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_ROLE, role)
            .putString(KEY_EXPIRED, expired)
            .putBoolean(KEY_LOGGED_IN, true)
            .apply();
    }

    public void logout() {
        prefs.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getRole() {
        return prefs.getString(KEY_ROLE, "MEMBER");
    }

    public String getExpired() {
        return prefs.getString(KEY_EXPIRED, "");
    }

    public boolean isOwner() {
        return getRole().equalsIgnoreCase("OWNER");
    }

    public boolean isVip() {
        String role = getRole();
        return role.equalsIgnoreCase("VIP") || role.equalsIgnoreCase("OWNER");
    }

    public boolean isReseller() {
        return getRole().equalsIgnoreCase("RESELLER");
    }
}
