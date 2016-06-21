package com.memento.android.themelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MaterialTheme.getThemeList().get(0).getThemeResId());
        super.onCreate(savedInstanceState);
    }
}
