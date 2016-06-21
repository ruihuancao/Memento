package com.memento.android.ui.main;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.data.model.Photo;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainFragment extends BaseFragment {


    @Bind(android.R.id.empty)
    ProgressBar empty;
    @Bind(R.id.image_grid)
    RecyclerView imageGrid;

    @Inject
    Repository repository;

    private PhotoAdapter photoAdapter;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        repository.getImageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Photo>>(){
                    @Override
                    public void onNext(List<Photo> photos) {
                        super.onNext(photos);
                        photoAdapter = new PhotoAdapter(getActivity(), photos);
                        imageGrid.setAdapter(photoAdapter);
                        empty.setVisibility(View.GONE);
                    }
                });

        return view;
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) imageGrid.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                /* emulating https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0B6Okdz75tqQsck9lUkgxNVZza1U/style_imagery_integration_scale1.png */
                switch (position % 6) {
                    case 5:
                        return 3;
                    case 3:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        imageGrid.addItemDecoration(new GridMarginDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        imageGrid.setHasFixedSize(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView author;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            photo = (ImageView)view.findViewById(R.id.photo);
            author = (TextView)view.findViewById(R.id.author);
        }
    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

        private final List<Photo> photos;
        private final Activity host;
        private final int requestedPhotoWidth;

        public PhotoAdapter(@NonNull Activity activity, @NonNull List<Photo> photos) {
            this.photos = photos;
            this.host = activity;
            requestedPhotoWidth = host.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(host).inflate(R.layout.view_photo_item, parent, false);
            PhotoViewHolder holder = new PhotoViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
            Photo photo = photos.get(position);
            Glide.with(host)
                    .load(photo.getPhotoUrl(requestedPhotoWidth))
                    .placeholder(R.color.white)
                    .override(ImageSize.NORMAL[0], ImageSize.NORMAL[1])
                    .into(holder.photo);
            holder.author.setText(photo.getAuthor());
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }

        @Override
        public long getItemId(int position) {
            return photos.get(position).getId();
        }
    }

    static class ImageSize {

        public static final int[] NORMAL = new int[]{480, 400};
        public static final int[] LARGE = new int[]{960, 800};
    }

    class GridMarginDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public GridMarginDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }
}
