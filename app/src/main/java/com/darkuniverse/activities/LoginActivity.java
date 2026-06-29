package com.darkuniverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkuniverse.R;
import com.darkuniverse.utils.SessionManager;
import com.darkuniverse.utils.TelegramBot;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private TextView tvError, tvRegisterHint;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        // Cek session
        if (sessionManager.isLoggedIn()) {
            navigateToDashboard();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);
        tvRegisterHint = findViewById(R.id.tvRegisterHint);

        btnLogin.setOnClickListener(v -> attemptLogin());

        tvRegisterHint.setOnClickListener(v -> {
            Toast.makeText(this, "Daftar via @Rvpa_jomok di Telegram", Toast.LENGTH_LONG).show();
            // Buka Telegram
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                    android.net.Uri.parse("https://t.me/Rvpa_jomok")));
            } catch (Exception e) {
                Toast.makeText(this, "Install Telegram dulu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Isi username dan password!");
            return;
        }

        tvError.setVisibility(View.GONE);

        // Simulasi login — nanti konek ke server
        // Untuk sekarang: login otomatis dengan role OWNER
        // TODO: Implement API call ke server

        // Login simulasi
        if (username.equals("admin") && password.equals("admin")) {
            sessionManager.login(username, "OWNER", "9999");
            navigateToDashboard();
        } else {
            // Coba login sebagai member
            sessionManager.login(username, "MEMBER", "30");
            navigateToDashboard();
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
