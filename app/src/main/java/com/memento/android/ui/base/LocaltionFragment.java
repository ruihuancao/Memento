package com.memento.android.ui.base;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 16:41
 * 描述：
 * 修改历史：
 */
public abstract class LocaltionFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, AMapLocationListener{

    private static final int RC_LOCATION_PERM = 121;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    protected void startLocaltion(){
    }

    protected abstract void localtionResult(AMapLocation amapLocation);


}