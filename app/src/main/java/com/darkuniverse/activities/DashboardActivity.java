package com.darkuniverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.darkuniverse.R;
import com.darkuniverse.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private SessionManager sessionManager;
    private TextView tvUsername, tvRole, navUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        tvUsername = findViewById(R.id.tvUsername);
        tvRole = findViewById(R.id.tvRole);

        tvUsername.setText(sessionManager.getUsername());
        tvRole.setText(sessionManager.getRole());

        // Nav header
        View headerView = navView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.navUsername);
        navUsername.setText(sessionManager.getUsername());

        // Card click
        CardView cardSender = findViewById(R.id.cardSender);
        CardView cardBug = findViewById(R.id.cardBug);
        CardView cardLogout = findViewById(R.id.cardLogout);

        cardSender.setOnClickListener(v -> {
            Intent intent = new Intent(this, SenderManageActivity.class);
            startActivity(intent);
        });

        cardBug.setOnClickListener(v -> {
            Intent intent = new Intent(this, BugActivity.class);
            startActivity(intent);
        });

        cardLogout.setOnClickListener(v -> logout());

        // Navigation drawer
        findViewById(R.id.toolbar).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.nav_sender) {
                startActivity(new Intent(this, SenderManageActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.nav_bug) {
                startActivity(new Intent(this, BugActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.nav_logout) {
                logout();
            }
            return true;
        });
    }

    private void logout() {
        sessionManager.logout();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
