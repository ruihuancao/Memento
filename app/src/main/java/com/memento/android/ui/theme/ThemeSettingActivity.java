package com.memento.android.ui.theme;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.data.repository.preference.SharePreferenceManager;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.widget.TransitionHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeSettingActivity extends BaseActivity {



    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    ColorAdapter mColorAdapter;

    @Inject
    Navigator mNavigator;

    @Inject
    SharePreferenceManager mSharePreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_theme_setting);
        ButterKnife.bind(this);
        setupWindowAnimations();
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.LEFT);
            slideTransition.setDuration(500);
            getWindow().setReenterTransition(slideTransition);
            getWindow().setExitTransition(slideTransition);
        }
    }

    private void initView(){
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mColorAdapter = new ColorAdapter(MaterialTheme.getThemeList());
        mRecyclerview.setAdapter(mColorAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {

        private List<MaterialTheme> mList;

        public ColorAdapter(List<MaterialTheme> list) {
            mList = list;
        }

        @Override
        public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_material_list_item, parent, false);
            ColorViewHolder colorViewHolder = new ColorViewHolder(view);
            return colorViewHolder;
        }

        @Override
        public void onBindViewHolder(ColorViewHolder holder, int position) {
            MaterialTheme materialTheme = getItem(position);
            holder.mListLeftIcon.setVisibility(View.VISIBLE);
            holder.mListLeftIcon.setBackgroundResource(materialTheme.getDrawResId());
            holder.mListTitle.setText(materialTheme.getNameResId());
            holder.mListSubtitle.setVisibility(View.GONE);
            holder.mListRightIcon.setVisibility(View.GONE);
            holder.itemView.setTag(getItem(position));

            final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(ThemeSettingActivity.this, true,
                    new Pair<>(holder.mListLeftIcon, getResources().getString(R.string.share_theme_image))
            );

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialTheme materialTheme = (MaterialTheme) v.getTag();
                    if (mSharePreferenceManager.getCurrentTheme().equals(materialTheme)) {
                    } else {
                        mSharePreferenceManager.addMaterilaTheme(materialTheme);
                    }
                    mNavigator.openMainActivity(ThemeSettingActivity.this, pairs);
                }
            });
        }

        private MaterialTheme getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {

        ImageView mListLeftIcon;
        TextView mListTitle;
        TextView mListSubtitle;
        ImageView mListRightIcon;

        public ColorViewHolder(View itemView) {
            super(itemView);
            mListLeftIcon = (ImageView) itemView.findViewById(R.id.list_left_icon);
            mListTitle = (TextView) itemView.findViewById(R.id.list_title);
            mListSubtitle = (TextView) itemView.findViewById(R.id.list_subtitle);
            mListRightIcon = (ImageView) itemView.findViewById(R.id.list_right_icon);
        }
    }

}
