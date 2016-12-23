package com.github.ruihuancao.theme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.updateTheme(this);
        super.onCreate(savedInstanceState);
    }
}
