package com.memento.android.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.memento.android.R;
import com.memento.android.model.ListItemModel;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 17:34
 * 描述：
 * 修改历史：
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager mLinearLayoutManager;

    public static MainFragment newInstance() {
        Logger.d("MainFragment create");
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        MainAdapter mainAdapter = new MainAdapter(getData());
        mRecyclerview.setAdapter(mainAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private List<ListItemModel> getData(){
        List<ListItemModel> listItemModels = new ArrayList<>();
        ListItemModel listItemModel = new ListItemModel("豆瓣电影", "好多好多电影", R.drawable.ic_local_movies_black_24dp);
        ListItemModel listItemModel1 = new ListItemModel("豆瓣电影", "好多好多电影", R.drawable.ic_local_movies_black_24dp);
        ListItemModel listItemModel2 = new ListItemModel("豆瓣电影", "好多好多电影", R.drawable.ic_local_movies_black_24dp);
        ListItemModel listItemModel3 = new ListItemModel("豆瓣电影", "好多好多电影", R.drawable.ic_local_movies_black_24dp);

        listItemModels.add(listItemModel);
        listItemModels.add(listItemModel1);
        listItemModels.add(listItemModel2);
        listItemModels.add(listItemModel3);

        return  listItemModels;
    }

    public class MainAdapter extends BaseQuickAdapter<ListItemModel> {

        public MainAdapter(List<ListItemModel> mList) {
            super(R.layout.layout_card_list_item, mList);
        }

        @Override
        protected void convert(BaseViewHolder helper, ListItemModel item) {
            helper.setText(R.id.list_title, item.getTitle())
                    .setText(R.id.list_subtitle, item.getSubTitle())
                    .setVisible(R.id.list_title, true)
                    .setVisible(R.id.list_subtitle, true)
                    .setVisible(R.id.list_left_icon, true)
                    .setImageResource(R.id.list_left_icon, item.getDrawRes());

//            Glide.with(mContext).load(item.getDrawRes()).crossFade().into((ImageView) helper.getView(R.id.list_left_icon));
        }
    }


}