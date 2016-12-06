package com.memento.android.ui.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.crh.android.common.login.LoginManager;
import com.crh.android.theme.RootActivity;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.ui.service.LocaltionService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class BaseActivity extends RootActivity implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final int RC_LOCATION_PERM = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void getPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE };
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            localtion();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.retionale_tip),
                    RC_LOCATION_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(perms.contains(Manifest.permission.ACCESS_FINE_LOCATION)){
            // 获取到了定位权限
            localtion();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, perms);
    }

    @Subscribe
    public void onEvent(Event.LocaltionResultEvent event) {
        stopService(new Intent(this, LocaltionService.class));
    }

    @Subscribe
    public void onEvent(Event.ShowMessageEvent event) {
    }

    @Subscribe
    public void onEvent(Event.LoginSuccessEvent event) {
        LoginManager.login(this, event.leanCloudUserEntiry);
    }

    @Subscribe
    public void onEvent(Event.RegisterSuccessEvent event) {
        LoginManager.login(this, event.leanCloudUserEntiry);
    }

    public void localtion(){
        startService(new Intent(this, LocaltionService.class));
    }
}