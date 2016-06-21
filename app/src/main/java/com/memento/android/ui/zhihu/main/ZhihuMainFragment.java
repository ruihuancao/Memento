package com.memento.android.ui.zhihu.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.memento.android.data.model.Article;
import com.memento.android.data.model.ArticleBanner;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.widget.banner.ConvenientBanner;
import com.memento.android.ui.widget.banner.holder.Holder;
import com.memento.android.ui.widget.banner.holder.ViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ZhihuMainFragment extends BaseFragment implements MainArticleContract.View {



    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;

    @Inject
    MainArticlePresenter mArticlePresenter;

    private List<Article> mList;
    private BlankAdapter mBlankAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private OnFragmentInteractionListener mListener;
    private String currentDate;

    private boolean isLoading = false;

    public ZhihuMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity)getActivity()).activityComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_main, container, false);
        ButterKnife.bind(this, view);
        mArticlePresenter.attachView(this);
        mList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mBlankAdapter = new BlankAdapter();
        mRecyclerview.setAdapter(mBlankAdapter);


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
                    mArticlePresenter.getNewArticle(currentDate);
                }
            }

        });
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    mArticlePresenter.getNewArticle();
                    isLoading = true;
                }
            }
        });
        mArticlePresenter.getNewArticle();
        mSwiperefreshlayout.setRefreshing(true);


        return view;
    }


    @Override
    public void showNewList(List<Article> mList) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList.clear();
        }
        isLoading = false;
        mSwiperefreshlayout.setRefreshing(false);
        if (mList != null && mList.size() > 0) {
            currentDate = mList.get(0).getDate();
            this.mList.addAll(mList);
            mBlankAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(mRecyclerview, "更新失败", Snackbar.LENGTH_INDEFINITE)
                    .setAction("再次刷新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mArticlePresenter.getNewArticle();
                        }
                    });
        }
    }

    @Override
    public void showList(List<Article> mList) {
        isLoading = false;
        if (mList != null && mList.size() > 0) {
            currentDate = mList.get(0).getDate();
            this.mList.addAll(mList);
            mBlankAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(mRecyclerview, "已经加载全部了", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mArticlePresenter.detachView();
    }

    public interface OnFragmentInteractionListener {
        void onClickListItem(Article article);
    }

    class BlankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item_banner, parent, false);
                return new BannerViewHolder(view);
            } else if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item_text, parent, false);
                return new TextViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_view_item, parent, false);
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
                        }, getItem(position).getArticleBanners())
                        .startTurning(8000)
                        .setPageIndicator(new int[]{R.drawable.circle_banner, R.drawable.circle_banner_press})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

            } else if (getItemViewType(position) == 1) {
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mTextView.setText(getItem(position).getTitle());
            } else {
                final Article article = getItem(position);
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                if (!TextUtils.isEmpty(getItem(position).getImageUrl())) {
                    Glide.with(getActivity()).load(getItem(position).getImageUrl()).into(listViewHolder.mImageView);
                }
                listViewHolder.mTitleView.setText(getItem(position).getTitle());
                listViewHolder.mSubTitleView.setVisibility(View.GONE);
                listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListener != null){
                            mListener.onClickListItem(article);
                        }
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

        public Article getItem(int position) {
            return mList.get(position);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_image)
        public ImageView mImageView;
        @Bind(R.id.card_text)
        public TextView mTitleView;
        @Bind(R.id.card_title)
        public TextView mSubTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bannerView)
        ConvenientBanner mBannerView;

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        TextView mTextView;

        public TextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class LocalImageHolderView implements Holder<ArticleBanner> {
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
        public void UpdateUI(Context context, int position, ArticleBanner data) {
            Glide.with(context).load(data.getUrl()).into(imageView);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setText(data.getTitle());
        }
    }

}
