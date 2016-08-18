package com.memento.android.model.mapper;

import android.content.Context;

import com.memento.android.R;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.injection.ApplicationContext;
import com.memento.android.model.ArticleModel;
import com.memento.android.model.ArticleBannerModel;
import com.memento.android.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataMapper {

    private Context mContext;

    @Inject
    public DataMapper(@ApplicationContext Context context) {
        this.mContext = context;
    }


    public List<ArticleModel> transform(ZhihuArticleEntity zhihuArticleEntity){
        List<ArticleModel> mList = null;
        boolean isToday = false;
        if(zhihuArticleEntity != null && zhihuArticleEntity.getTop_stories() != null
                && zhihuArticleEntity.getTop_stories().size() > 0){
            isToday = true;
            if(mList == null){
                mList = new ArrayList<>();
            }
            ArticleModel articleModel = new ArticleModel();
            articleModel.setType(0);
            articleModel.setDate(zhihuArticleEntity.getDate());
            List<ArticleBannerModel> mBanner = new ArrayList<>();
            ArticleBannerModel articleBannerModel = null;
            for (ZhihuArticleEntity.TopStoriesEntity topStoriesEntity : zhihuArticleEntity.getTop_stories()){
                articleBannerModel = new ArticleBannerModel();
                articleBannerModel.setUrl(topStoriesEntity.getImage());
                articleBannerModel.setTitle(topStoriesEntity.getTitle());
                articleBannerModel.setId(String.valueOf(topStoriesEntity.getId()));
                mBanner.add(articleBannerModel);
            }
            articleModel.setArticleBannerModels(mBanner);
            mList.add(articleModel);
        }
        if(zhihuArticleEntity != null && zhihuArticleEntity.getStories() != null
                && zhihuArticleEntity.getStories().size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }

            ArticleModel titleArticleModel = new ArticleModel();
            titleArticleModel.setType(1);
            titleArticleModel.setDate(zhihuArticleEntity.getDate());
            titleArticleModel.setTitle(TimeUtil.converDate(zhihuArticleEntity.getDate()));
            mList.add(titleArticleModel);
            if(isToday){
                titleArticleModel.setTitle(mContext.getResources().getString(R.string.today_list_title));
            }

            ArticleModel articleModel = null;
            for (ZhihuArticleEntity.StoriesEntity storiesEntity : zhihuArticleEntity.getStories()){
                articleModel = new ArticleModel();
                if(storiesEntity.getImages() != null && storiesEntity.getImages().size() > 0){
                    articleModel.setImageUrl(storiesEntity.getImages().get(0));
                }
                articleModel.setTitle(storiesEntity.getTitle());
                articleModel.setId(String.valueOf(storiesEntity.getId()));
                articleModel.setDate(zhihuArticleEntity.getDate());
                articleModel.setType(2);
                mList.add(articleModel);
            }
        }
        return mList;
    }
}  