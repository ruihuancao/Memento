package com.memento.android.ui.douban.movie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.widget.DividerItemDecoration;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 19:33
 * 描述：
 * 修改历史：
 */
public class CommonMovieFragment extends BaseFragment {

    public static final int TOP250_TYPE = 0;
    public static final int COMINGSOON_TYPE = 1;

    public static final int PAGE_SIZE = 10;

    private static final String TYPE = "TYPE";

    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager mLinearLayoutManager;
    private Adapter mAdapter;
    private int type;
    private DouBanMovieEntity mDouBanMovieEntity;
    private boolean isLoading = false;
    private Subscription mSubscription;

    @Inject
    Repository mRepository;


    public void Top250Fragment() {
    }

    public static CommonMovieFragment newInstance(int type) {
        Logger.d("Top250Fragment create");
        CommonMovieFragment fragment = new CommonMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity)getActivity()).activityComponent().inject(this);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban_top250, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        initData();
        return view;
    }

    private void setupRecyclerView(){
        mAdapter = new Adapter();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
      /*  mRecyclerview.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));*/
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerview.setAdapter(mAdapter);

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && (lastVisibleItem > totalItemCount - 3) && dy > 0) {
                    if((mDouBanMovieEntity.getStart() + mDouBanMovieEntity.getCount()) < mDouBanMovieEntity.getTotal()){
                        isLoading = true;
                        loadMore(mDouBanMovieEntity.getStart()+mDouBanMovieEntity.getCount(), mDouBanMovieEntity.getCount());
                    }
                }
            }

        });
    }

    private void initData(){
        loadMore(0, PAGE_SIZE);
    }

    private void loadMore(int start, int count){
        switch (type){
            case TOP250_TYPE:
                mSubscription = mRepository.getTop250Movie(start, count)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultSubscriber<DouBanMovieEntity>(){
                            @Override
                            public void onNext(DouBanMovieEntity douBanMovieEntity) {
                                super.onNext(douBanMovieEntity);
                                hideProgress();
                                mDouBanMovieEntity = douBanMovieEntity;
                                mAdapter.addList(douBanMovieEntity.getSubjects());
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                hideProgress();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                hideProgress();
                            }
                        });
                break;
            case COMINGSOON_TYPE:
                mSubscription = mRepository.getComingSoonMovie(start, count)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultSubscriber<DouBanMovieEntity>(){
                            @Override
                            public void onNext(DouBanMovieEntity douBanMovieEntity) {
                                super.onNext(douBanMovieEntity);
                                hideProgress();
                                mDouBanMovieEntity = douBanMovieEntity;
                                mAdapter.addList(douBanMovieEntity.getSubjects());
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                hideProgress();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                hideProgress();
                            }
                        });
                break;

        }
    }

    private void hideProgress(){
        isLoading = false;
        if(mProgressbar != null && mProgressbar.getVisibility() == View.VISIBLE){
            mProgressbar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(mSubscription != null){
            mSubscription.unsubscribe();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_common_list_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ListViewHolder holder, int position) {
            DouBanMovieEntity.SubjectsEntity subjectsEntity = getItem(position);
            holder.mTitleView.setText(subjectsEntity.getTitle());
            if(subjectsEntity.getDirectors().size() > 0){
                holder.mSubTitleView.setText(subjectsEntity.getDirectors().get(0).getName());
            }
            Glide.with(getActivity()).load(subjectsEntity.getImages().getSmall()).asBitmap().fitCenter().into(new BitmapImageViewTarget(holder.mImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.mImageView.setImageDrawable(circularBitmapDrawable);
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

        @Bind(R.id.thumbnail)
        public ImageView mImageView;
        @Bind(R.id.list_title)
        public TextView mTitleView;
        @Bind(R.id.list_subtitle)
        public TextView mSubTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}  