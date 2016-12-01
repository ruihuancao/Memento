package com.crh.android.common.theme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.updateTheme(this);
        super.onCreate(savedInstanceState);
    }
}
