package com.memento.android.ui.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.memento.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomActivity extends AppCompatActivity {

    @BindView(R.id.contentView)
    CustomView contentView;
    @BindView(R.id.contentLayout)
    PView contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);

        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomActivity.this, "test", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getCallIntent(Context context) {
        return new Intent(context, CustomActivity.class);
    }
}
