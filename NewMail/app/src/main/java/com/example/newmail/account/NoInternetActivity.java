package com.example.newmail.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.newmail.MainActivity;
import com.example.newmail.R;
import com.example.newmail.network.ConnectionDetector;

public class NoInternetActivity extends AppCompatActivity {
    ConnectionDetector detector;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        txt = findViewById(R.id.Error_Internet_Connection);
        detector = new ConnectionDetector(this);
    }

    public void tryRestoreActivities(View view) {
        if (detector.isConnectingToInternet())
            finish();
        else {
            txt.setError("");
            (new Handler()).postDelayed(() -> {
                txt.setError(null);
            }, 500);
        }
    }
}