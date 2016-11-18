package com.memento.android.ui.zhihu.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.helper.TransitionHelper;
import com.memento.android.bean.ArticleBannerModel;
import com.memento.android.bean.ArticleModel;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.widget.DividerItemDecoration;
import com.memento.android.widget.banner.ConvenientBanner;
import com.memento.android.widget.banner.holder.Holder;
import com.memento.android.widget.banner.holder.ViewHolderCreator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ZhihuMainFragment extends BaseFragment implements ZhihuContract.View{



    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;

    private Unbinder mUnbinder;
    private List<ArticleModel> mList;
    private Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private String currentDate;
    private boolean isLoading = false;
    private ZhihuContract.Presenter mPresenter;

    public ZhihuMainFragment() {
    }


    public static ZhihuMainFragment newInstance() {
        return new ZhihuMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new Adapter();
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if(mList.get(firstItem).getType() == 1){
                    ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                    if(actionBar != null){
                        actionBar.setTitle(mList.get(firstItem).getTitle());
                    }
                }
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && (lastVisibleItem > totalItemCount - 3) && dy > 0) {
                    isLoading = true;
                    mPresenter.getNextArticle(currentDate);
                }
            }

        });
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    mPresenter.getNewArticle();
                }
            }
        });
        mPresenter.subscribe();
        mSwiperefreshlayout.setRefreshing(true);
        return view;
    }

    @Override
    public void setPresenter(ZhihuContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showNextList(List<ArticleModel> articleModelList) {
        isLoading = false;
        if (articleModelList != null && articleModelList.size() > 0) {
            currentDate = articleModelList.get(0).getDate();
            this.mList.addAll(articleModelList);
            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(mRecyclerview, "已经加载全部了", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public void showNewList(List<ArticleModel> articleModelList) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList.clear();
        }
        isLoading = false;
        mSwiperefreshlayout.setRefreshing(false);
        if (articleModelList != null && articleModelList.size() > 0) {
            currentDate = articleModelList.get(0).getDate();
            this.mList.addAll(articleModelList);
            mAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(mRecyclerview, "更新失败", Snackbar.LENGTH_INDEFINITE)
                    .setAction("再次刷新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.getNewArticle();
                        }
                    });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        mUnbinder.unbind();
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item_banner, parent, false);
                return new BannerViewHolder(view);
            } else if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_title, parent, false);
                return new TextViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_common_list_item, parent, false);
                return new ListViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (getItemViewType(position) == 0) {
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerViewHolder.mBannerView.setPages(
                        new ViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, getItem(position).getArticleBannerModels())
                        .startTurning(8000)
                        .setPageIndicator(new int[]{R.drawable.circle_banner, R.drawable.circle_banner_press})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

            } else if (getItemViewType(position) == 1) {
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mTextView.setText(getItem(position).getTitle());
            } else {
                final ArticleModel articleModel = getItem(position);
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                if (!TextUtils.isEmpty(getItem(position).getImageUrl())) {
                    Glide.with(getActivity()).load(getItem(position).getImageUrl()).into(listViewHolder.mImageView);
                }
                listViewHolder.mTitleView.setText(getItem(position).getTitle());
                listViewHolder.mSubTitleView.setVisibility(View.GONE);

                listViewHolder.itemView.setTag(articleModel.getId());
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true,
                        new Pair<>(listViewHolder.mImageView, getActivity().getString(R.string.share_list_image))
                        );

                listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new Event.OpenZhihuDetailActivity(articleModel.getId(), pairs));
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getType();
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public ArticleModel getItem(int position) {
            return mList.get(position);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        public ImageView mImageView;
        @BindView(R.id.list_title)
        public TextView mTitleView;
        @BindView(R.id.list_subtitle)
        public TextView mSubTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bannerView)
        ConvenientBanner mBannerView;

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView mTextView;

        public TextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class LocalImageHolderView implements Holder<ArticleBannerModel> {
        private ImageView imageView;
        private TextView mTextView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_banner, null);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            mTextView = (TextView)view.findViewById(R.id.textView);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, final ArticleBannerModel data) {
            Glide.with(context).load(data.getUrl()).into(imageView);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setText(data.getTitle());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new Event.OpenZhihuDetailActivity(data.getId(), null));
                }
            });
        }
    }

}
