package com.memento.android.ui.main;

import android.Manifest;
import android.app.Activity;
import android.graphics.Rect;
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
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PhotoFragment extends BaseFragment  implements EasyPermissions.PermissionCallbacks, AMapLocationListener {

    private static final int RC_LOCATION_PERM = 120;


    @Bind(android.R.id.empty)
    ProgressBar empty;
    @Bind(R.id.image_grid)
    RecyclerView imageGrid;

    @Inject
    Repository repository;

    private PhotoAdapter photoAdapter;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        getLocaltionAndStorePermission();
        return view;
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
        mLocationOption.setOnceLocation(false);
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

                getData();

                mLocationClient.stopLocation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void getData(){
        repository.getImageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<SplashImageEntity>>(){
                    @Override
                    public void onNext(List<SplashImageEntity> photos) {
                        super.onNext(photos);
                        photoAdapter = new PhotoAdapter(getActivity(), photos);
                        imageGrid.setAdapter(photoAdapter);
                        empty.setVisibility(View.GONE);
                    }
                });
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void getLocaltionAndStorePermission() {
        String[] perms = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION };
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            initLocaltion();
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

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, perms);
    }




    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) imageGrid.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position % 6) {
                    case 5:
                        return 3;
                    case 3:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        imageGrid.addItemDecoration(new GridMarginDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        imageGrid.setHasFixedSize(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(mLocationClient != null){
            mLocationClient.onDestroy();
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView author;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            photo = (ImageView)view.findViewById(R.id.photo);
            author = (TextView)view.findViewById(R.id.text);
        }
    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

        private final List<SplashImageEntity> photos;
        private final Activity host;
        private final int requestedPhotoWidth;

        public PhotoAdapter(@NonNull Activity activity, @NonNull List<SplashImageEntity> photos) {
            this.photos = photos;
            this.host = activity;
            requestedPhotoWidth = host.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(host).inflate(R.layout.layout_photo_card, parent, false);
            PhotoViewHolder holder = new PhotoViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
            SplashImageEntity photo = photos.get(position);
            Glide.with(host)
                    .load(photo.getPhotoUrl(requestedPhotoWidth))
                    .placeholder(R.color.white)
                    .override(ImageSize.NORMAL[0], ImageSize.NORMAL[1])
                    .into(holder.photo);
            holder.author.setText(photo.getAuthor());
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }

        @Override
        public long getItemId(int position) {
            return photos.get(position).getId();
        }
    }

    static class ImageSize {

        public static final int[] NORMAL = new int[]{480, 400};
        public static final int[] LARGE = new int[]{960, 800};
    }

    class GridMarginDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public GridMarginDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }
}
