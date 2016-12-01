package com.memento.android.ui.douban.movie;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.bumptech.glide.Glide;
import com.crh.android.common.data.source.entity.DouBanMovieEntity;
import com.crh.android.common.subscriber.DefaultSubscriber;
import com.memento.android.R;
import com.memento.android.helper.DataHelper;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.webview.CustomTabActivityHelper;
import com.memento.android.ui.webview.WebviewFallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 18:57
 * 描述：
 * 修改历史：
 */
public class TheatersMovieFragment extends BaseFragment{

    private static final String MOBILE_URL = "https://movie.douban.com/subject/%s/mobile";

    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private Unbinder mUnbinder;

    private CompositeSubscription compositeSubscription;
    private Adapter  mAdapter;
    private GridLayoutManager mGridLayoutManager;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    public void TheatersMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static TheatersMovieFragment newInstance() {
        return new TheatersMovieFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_theater_movie, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        compositeSubscription = new CompositeSubscription();
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mAdapter = new Adapter();
        mRecyclerview.setAdapter(mAdapter);
        localtionResult();
        return view;
    }


    protected void localtionResult() {
        Subscription subscription = DataHelper.provideRepository(getActivity().getApplicationContext()).getTheatersMovie("杭州")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<DouBanMovieEntity>(){

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
        compositeSubscription.add(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeSubscription.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
            holder.mFrameLayout.setTag(subjectsEntity);
            holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DouBanMovieEntity.SubjectsEntity subjectsEntity = (DouBanMovieEntity.SubjectsEntity)v.getTag();
                    String mobileUrl = String.format(MOBILE_URL, subjectsEntity.getId());
                    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                    CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent, Uri.parse(mobileUrl), new WebviewFallback());
                }
            });
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

        @BindView(R.id.photo)
        ImageView mImageView;
        @BindView(R.id.name)
        TextView mTitleView;
        @BindView(R.id.contentLayout)
        FrameLayout mFrameLayout;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}