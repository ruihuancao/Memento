package com.memento.android.ui.douban.movie;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.theme.MaterialTheme;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TestFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private Unbinder mUnbinder;

    public TestFragment() {
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_theme_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        Adapter adapter = new Adapter(MaterialTheme.getThemeList());
        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_color);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        private List<MaterialTheme> mList;

        public Adapter(List<MaterialTheme> mList) {
            this.mList = mList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_theme_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            MaterialTheme materialTheme = mList.get(position);
            holder.mImageView.setImageResource(materialTheme.getDrawResId());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
