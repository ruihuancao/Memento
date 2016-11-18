package com.memento.android.ui.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PeosonFragment extends BaseFragment {

    public static final String TAG = PeopleFragment.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private Unbinder mUnbinder;

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;


    public PeosonFragment() {
    }

    public static PeosonFragment newInstance() {
        Logger.d("PeosonFragment create");
        PeosonFragment fragment = new PeosonFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peoson, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mRecyclerview.setAdapter(new PeosonAdapter());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    class PeosonAdapter extends RecyclerView.Adapter<ListViewHolder> {

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_main_list_item, parent, false);
            ListViewHolder vh = new ListViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 21;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.info_text)
        TextView infoText;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
