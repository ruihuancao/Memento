package com.memento.android.ui.douban.movie;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.subscriber.ProgressSubscriver;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 18:57
 * 描述：
 * 修改历史：
 */
public class TheatersMovieFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, AMapLocationListener {

    private static final int RC_LOCATION_PERM = 121;


    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private Adapter  mAdapter;
    private GridLayoutManager mGridLayoutManager;

    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    @Inject
    Repository mRepository;

    public void TheatersMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity)getActivity()).activityComponent().inject(this);
    }

    public static TheatersMovieFragment newInstance() {
        Logger.d("TheatersMovieFragment create");
        TheatersMovieFragment fragment = new TheatersMovieFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_theater_movie, container, false);
        ButterKnife.bind(this, view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mAdapter = new Adapter();
        mRecyclerview.setAdapter(mAdapter);
        getLocaltionPermission();
        return view;
    }


    protected void localtionResult(AMapLocation amapLocation) {

        mRepository.getTheatersMovie("杭州")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriver<DouBanMovieEntity>(getContext()){

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(DouBanMovieEntity douBanMovieEntity) {
                        super.onNext(douBanMovieEntity);
                        mAdapter.addList(douBanMovieEntity.getSubjects());
                    }
                });
    }


    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void getLocaltionPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE };
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            initLocaltion();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.retionale_tip),
                    RC_LOCATION_PERM, perms);
        }
    }

    private void initLocaltion(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                Log.e("AmapLocaltion", amapLocation.getCountry() + amapLocation.getProvince() +amapLocation.getCity());
                localtionResult(amapLocation);
                mLocationClient.stopLocation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                localtionResult(amapLocation);
                mLocationClient.stopLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("test", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, perms);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(mLocationClient != null){
            mLocationClient.onDestroy();
        }
    }

    class Adapter extends RecyclerView.Adapter<ListViewHolder>{

        private List<DouBanMovieEntity.SubjectsEntity> mList;

        public Adapter() {
            mList = new ArrayList<>();
        }

        public void addList(List<DouBanMovieEntity.SubjectsEntity> list){
            this.mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_douban_movie_list_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            DouBanMovieEntity.SubjectsEntity subjectsEntity = getItem(position);
            Glide.with(getActivity()).load(subjectsEntity.getImages().getLarge()).crossFade().into(holder.mImageView);
            holder.mTitleView.setText(subjectsEntity.getTitle());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private DouBanMovieEntity.SubjectsEntity getItem(int position){
            return mList.get(position);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo)
        public ImageView mImageView;
        @Bind(R.id.name)
        public TextView mTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}