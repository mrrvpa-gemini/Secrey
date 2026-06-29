package com.darkuniverse.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.darkuniverse.R;
import com.darkuniverse.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class SenderManageActivity extends AppCompatActivity {
    private TextInputEditText etNumber;
    private MaterialButton btnPairing;
    private TextView tvResult;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etNumber = findViewById(R.id.etNumber);
        btnPairing = findViewById(R.id.btnPairing);
        tvResult = findViewById(R.id.tvPairingResult);
        progressBar = findViewById(R.id.progressBar);

        btnPairing.setOnClickListener(v -> generatePairing());
    }

    private void generatePairing() {
        String number = etNumber.getText().toString().trim();
        if (number.isEmpty()) {
            Toast.makeText(this, "Masukkan nomor target!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnPairing.setEnabled(false);
        tvResult.setVisibility(View.GONE);

        // Simulasi generate pairing code
        btnPairing.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            btnPairing.setEnabled(true);

            String code = generateRandomCode();
            tvResult.setText("✅ Pairing Code: " + code + "\n📱 Target: " + number + "\n\nKirim ke target via WhatsApp");
            tvResult.setVisibility(View.VISIBLE);
        }, 2000);
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
