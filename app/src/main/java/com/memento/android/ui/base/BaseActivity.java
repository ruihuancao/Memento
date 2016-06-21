package com.memento.android.ui.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.memento.android.MementoApplication;
import com.memento.android.R;
import com.memento.android.injection.component.ActivityComponent;
import com.memento.android.injection.component.DaggerActivityComponent;
import com.memento.android.injection.module.ActivityModule;
import com.memento.android.navigation.Navigator;
import com.memento.android.themelibrary.SetThemeDialogFragment;
import com.memento.android.themelibrary.ThemeActivity;

import javax.inject.Inject;


public class BaseActivity extends ThemeActivity {

    public static final String PARAM_ONE = "param_one";
    protected ActivityComponent mActivityComponent;

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MementoApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.primary_dark));
    }

    public void onNavItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.nav_zhihu) {
            mNavigator.openZhihuMainActivity(this);
        } else if (id == R.id.nav_dingxiang) {

        } else if (id == R.id.nav_wxjingxuan) {

        } else if (id == R.id.nav_joke) {

        } else if (id == R.id.nav_pic) {

        } else if (id == R.id.nav_send) {

        }else if(id  == R.id.nav_setting){
            DialogFragment dialogFragment = new SetThemeDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "test");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}