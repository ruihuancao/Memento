package com.memento.android.ui.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.memento.android.R;
import com.memento.android.ui.theme.MaterialTheme;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-29
 * 时间: 19:04
 * 描述：
 * 修改历史：
 */
public class ColorPickerPreference extends DialogPreference {

    public ColorPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.layout_theme_list);
        setDialogIcon(null);
    }


    @Override
    protected View onCreateDialogView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(getDialogLayoutResource(), null);
        RecyclerView mRecyclerview = (RecyclerView)view.findViewById(R.id.recyclerview);
        Adapter adapter = new Adapter(MaterialTheme.getThemeList());
        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerview.setAdapter(adapter);
        return view;
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