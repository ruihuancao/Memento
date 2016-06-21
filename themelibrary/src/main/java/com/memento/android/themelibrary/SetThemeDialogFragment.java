package com.memento.android.themelibrary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;


public class SetThemeDialogFragment extends DialogFragment {


    public SetThemeDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_theme_dialog, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ThemeAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            }
        });

        return view;
    }

    public class ThemeAdapter extends BaseAdapter {
        private Context mContext;

        public ThemeAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return MaterialTheme.getThemeList().size();
        }

        public MaterialTheme getItem(int position) {
            return MaterialTheme.getThemeList().get(position);
        }

        public long getItemId(int position) {
            return MaterialTheme.getThemeList().get(position).getNameResId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CircleView circleView;
            if (convertView == null) {
                circleView = (CircleView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_color, null);
            } else {
                circleView = (CircleView) convertView;
            }
            circleView.setColorResId(getItem(position).getColorResId());
            return circleView;
        }

    }

}
