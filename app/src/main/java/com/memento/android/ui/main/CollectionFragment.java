package com.memento.android.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectionFragment extends BaseFragment {

    public static final String TAG = CollectionFragment.class.getSimpleName();

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;

    private GridLayoutManager mGridLayoutManager;

    public CollectionFragment() {
    }


    public static CollectionFragment newInstance() {
        Logger.d("CollectionFragment create");
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerview.setAdapter(new CollectionAdapter());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    static class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {



        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView mInfoText;
            CardView mCardView;

            public ViewHolder(View itemView) {
                super(itemView);
                mInfoText = (TextView)itemView.findViewById(R.id.info_text);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_main_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position % 2 == 0){
                holder.mInfoText.setText("使用replace方式，虽然这种方式会避免上述的bug，但也是重复创建了对象。因为replace方式，对应的FrameLayout只有一 层，而add方式，这个FrameLayout其实有2层。但是这种方式的缺点是：每次replace会把生命周期全部执行一遍，如果在这些生命周期函数 里拉取数据的话，就会不断重复的加载刷新数据。");
            }
        }

        @Override
        public int getItemCount() {
            return 21;
        }
    }
}
