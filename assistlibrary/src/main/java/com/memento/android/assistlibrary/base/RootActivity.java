package com.memento.android.assistlibrary.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.memento.android.assistlibrary.theme.ThemeHelper;


public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.updateTheme(this);
        super.onCreate(savedInstanceState);
    }
}
