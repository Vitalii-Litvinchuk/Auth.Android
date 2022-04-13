package com.example.newmail.constants;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class Methods {
    public static void enableProgressBarWithButton( ProgressBar progBar,Button btn) {
        progBar.setVisibility(View.VISIBLE);
        btn.setVisibility(View.INVISIBLE);
    }

    public static void disableProgressBarWithButton( ProgressBar progBar ,Button btn) {
        btn.setVisibility(View.VISIBLE);
        progBar.setVisibility(View.INVISIBLE);
    }

    public static void enableProgressBar(ProgressBar progBar) {
        progBar.setVisibility(View.VISIBLE);
    }

    public static void disableProgressBar(ProgressBar progBar) {
        progBar.setVisibility(View.INVISIBLE);
    }
}
