package com.memento.android.ui.preference;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.memento.android.R;
import com.memento.android.event.UpdateNavMenuEvent;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SettingsActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_KEY_THEME = "pref_key_theme_setting";
    public static final String FUNCTION_DOUBAN_MOIVE_KEY = "pref_key_douban_moive_setting";
    public static final String FUNCTION_DOUBAN_BOOK_KEY = "pref_key_douban_book_setting";
    public static final String FUNCTION_ZHIHU_KEY = "pref_key_zhihu_setting";
    public static final String FUNCTION_WEIXIN_KEY = "pref_key_weixin_setting";
    public static final String FUNCTION_MEITU_KEY = "pref_key_meitu_setting";
    public static final String FUNCTION_DXYS_KEY = "pref_key_dxys_setting";


    @Inject
    Navigator mNavigator;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().
                    replace(R.id.setting_content, new GeneralPreferenceFragment()).commit();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(FUNCTION_DOUBAN_MOIVE_KEY) || key.equals(FUNCTION_DOUBAN_BOOK_KEY) ||
                key.equals(FUNCTION_ZHIHU_KEY) || key.equals(FUNCTION_WEIXIN_KEY) ||
                key.equals(FUNCTION_DXYS_KEY) || key.equals(FUNCTION_MEITU_KEY)){
            EventBus.getDefault().post(new UpdateNavMenuEvent());
        }else if(key.equals(PREF_KEY_THEME)){
            mNavigator.openReStartMainActivity(this);
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }

    }
}
