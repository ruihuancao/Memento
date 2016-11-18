package com.memento.android.ui.main;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.assistlibrary.view.glide.GlideHelper;
import com.memento.android.bean.ListItemModel;
import com.memento.android.assistlibrary.util.ActivityUtils;
import com.memento.android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 17:34
 * 描述：
 * 修改历史：
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private Unbinder mUnbinder;
    private LinearLayoutManager mLinearLayoutManager;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        MainAdapter mainAdapter = new MainAdapter(getData());
        mRecyclerview.setAdapter(mainAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private List<ListItemModel> getData(){
        List<ListItemModel> listItemModels = new ArrayList<>();
        ListItemModel listItemModel = new ListItemModel("知乎日报", "", ActivityUtils.getQiniuImageUrl());
        ListItemModel listItemModel1 = new ListItemModel("豆瓣电影", "", ActivityUtils.getQiniuImageUrl());
        ListItemModel listItemModel2 = new ListItemModel("豆瓣读书", "", ActivityUtils.getQiniuImageUrl());
        ListItemModel listItemModel3 = new ListItemModel("每日一刻", "", ActivityUtils.getQiniuImageUrl());

        listItemModels.add(listItemModel);
        listItemModels.add(listItemModel1);
        listItemModels.add(listItemModel2);
        listItemModels.add(listItemModel3);

        return  listItemModels;
    }

    class MainAdapter extends RecyclerView.Adapter<ListViewHolder> {

        List<ListItemModel> mList;

        public MainAdapter(List<ListItemModel> mList){
            this.mList = mList;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_main_list_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            String url = "http://pic4.zhimg.com/bc46ea6ea037d8c94e87104a10c0e647.jpg";
            ListItemModel listItemModel = mList.get(position);
            GlideHelper.loadResource(listItemModel.getImageUrl(), holder.cardImage);

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_image)
        ImageView cardImage;
        @BindView(R.id.card_primary_title)
        TextView cardPrimaryTitle;
        @BindView(R.id.card_primary_subtitle)
        TextView cardPrimarySubtitle;
        @BindView(R.id.card_primary_title_layout)
        LinearLayout cardPrimaryTitleLayout;
        @BindView(R.id.card_view)
        CardView cardView;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}