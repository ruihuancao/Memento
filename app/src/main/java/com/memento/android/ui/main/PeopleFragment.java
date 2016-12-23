package com.memento.android.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.memento.android.helper.GlideHelper;
import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PeopleFragment extends BaseFragment {

    public static final String TAG = PeopleFragment.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private Unbinder mUnbinder;

    LinearLayoutManager mLinearLayoutManager;

    public PeopleFragment() {
    }


    public static PeopleFragment newInstance() {
        Logger.d("PeopleFragment create");
        PeopleFragment fragment = new PeopleFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(new PeopleAdapter());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    class PeopleAdapter extends RecyclerView.Adapter<ListViewHolder> {


        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card_large, parent, false);
            ListViewHolder vh = new ListViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            String url = "http://pic4.zhimg.com/bc46ea6ea037d8c94e87104a10c0e647.jpg";
            GlideHelper.loadResource(url, holder.cardImage);
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "send message"));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 21;
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
        @BindView(R.id.favorite_button)
        ImageButton favoriteButton;
        @BindView(R.id.share_button)
        ImageButton shareButton;
        @BindView(R.id.action_layout)
        LinearLayout actionLayout;
        @BindView(R.id.card_view)
        CardView cardView;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
