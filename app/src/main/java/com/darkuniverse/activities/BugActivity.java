package com.darkuniverse.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.darkuniverse.R;
import com.darkuniverse.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BugActivity extends AppCompatActivity {
    private Spinner spinnerBug;
    private TextInputEditText etTarget;
    private MaterialButton btnExecute;
    private TextView tvResult;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    private final Map<String, String> bugMethods = new HashMap<>();
    private String selectedBug = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        spinnerBug = findViewById(R.id.spinnerBug);
        etTarget = findViewById(R.id.etTarget);
        btnExecute = findViewById(R.id.btnExecute);
        tvResult = findViewById(R.id.tvBugResult);
        progressBar = findViewById(R.id.progressBar);

        setupBugMethods();
        setupSpinner();

        btnExecute.setOnClickListener(v -> executeBug());
    }

    private void setupBugMethods() {
        bugMethods.put("📱 Delay X Freeze", "delayxfreeze");
        bugMethods.put("📱 Delay X Invis", "delayxinvis");
        bugMethods.put("📱 Crash CRL", "crashcrl");
        bugMethods.put("📱 Drakvers FC", "drakvers-fc");
        bugMethods.put("📱 Drakvers Buldo", "drakvers-bulldo");
        bugMethods.put("📱 Drakvers UI", "drakvers-ui");
        bugMethods.put("📱 Delay BBSSpam", "delay-bbsspam");
        bugMethods.put("📱 Freeze X Blank", "freezexblank");
        bugMethods.put("📱 Force Docu", "forcedocu");
    }

    private void setupSpinner() {
        String[] items = bugMethods.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBug.setAdapter(adapter);

        spinnerBug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBug = bugMethods.get(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedBug = "";
            }
        });
    }

    private void executeBug() {
        String target = etTarget.getText().toString().trim();
        if (target.isEmpty()) {
            Toast.makeText(this, "Masukkan target number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedBug.isEmpty()) {
            Toast.makeText(this, "Pilih bug method!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnExecute.setEnabled(false);
        tvResult.setVisibility(View.GONE);

        btnExecute.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            btnExecute.setEnabled(true);

            String result = "🔥 *Bug Executed!*\n" +
                           "📱 Method: " + selectedBug + "\n" +
                           "🎯 Target: " + target + "\n" +
                           "📊 Status: " + (new Random().nextBoolean() ? "✅ Success" : "⚠️ Partial") + "\n" +
                           "⏱️ " + (System.currentTimeMillis() % 1000) + "ms\n\n" +
                           "💀 Dark Universe by @Rvpa_jomok";

            tvResult.setText(result);
            tvResult.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Bug executed on " + target, Toast.LENGTH_SHORT).show();
        }, 3000);
    }
}
