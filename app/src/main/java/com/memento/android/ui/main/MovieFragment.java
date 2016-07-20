package com.memento.android.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-27
 * 时间: 16:39
 * 描述：
 * 修改历史：
 */
public class MovieFragment extends BaseFragment {


    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private Unbinder mUnbinder;

    public void MovieFragment(){}

    public static MovieFragment newInstance() {
        Logger.d("MovieFragment create");
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerview.setVisibility(View.GONE);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}