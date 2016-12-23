package com.memento.android.ui.setting;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.github.ruihuancao.theme.ThemeHelper;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String FUNCTION_DOUBAN_MOIVE_KEY = "pref_key_douban_moive_setting";
    public static final String FUNCTION_DOUBAN_BOOK_KEY = "pref_key_douban_book_setting";
    public static final String FUNCTION_ZHIHU_KEY = "pref_key_zhihu_setting";
    public static final String FUNCTION_WEIXIN_KEY = "pref_key_weixin_setting";
    public static final String FUNCTION_MEITU_KEY = "pref_key_meitu_setting";
    public static final String FUNCTION_DXYS_KEY = "pref_key_dxys_setting";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().
                    replace(R.id.setting_content, new GeneralPreferenceFragment()).commit();
        }
    }


    public static Intent getCallIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(FUNCTION_DOUBAN_MOIVE_KEY) || key.equals(FUNCTION_DOUBAN_BOOK_KEY) ||
                key.equals(FUNCTION_ZHIHU_KEY) || key.equals(FUNCTION_WEIXIN_KEY) ||
                key.equals(FUNCTION_DXYS_KEY) || key.equals(FUNCTION_MEITU_KEY)){
            EventBus.getDefault().post(new Event.UpdateNavMenuEvent());
        }else if(key.equals(ThemeHelper.PREF_KEY_THEME)){
            Intent intent = MainActivity.getCallIntent(SettingsActivity.this);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
