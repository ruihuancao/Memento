package com.memento.android.data.mapper;

import android.content.Context;

import com.memento.android.R;
import com.memento.android.data.entity.IpAddress;
import com.memento.android.data.entity.SplashImage;
import com.memento.android.data.entity.ZhihuArticle;
import com.memento.android.data.entity.ZhihuArticleDetail;
import com.memento.android.data.entity.ZhihuLauncherImage;
import com.memento.android.data.model.Address;
import com.memento.android.data.model.Article;
import com.memento.android.data.model.ArticleBanner;
import com.memento.android.data.model.ArticleDetail;
import com.memento.android.data.model.LauncherImage;
import com.memento.android.data.model.Photo;
import com.memento.android.injection.ApplicationContext;
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


    public List<Photo> transform(List<SplashImage> list){
        List<Photo> results = new ArrayList<>();
        for(SplashImage splashImage : list){
            results.add(new Photo(splashImage.getId(), splashImage.getAuthor()));
        }
        return results;
    }

    public LauncherImage transform(ZhihuLauncherImage zhihuLauncherImage){
        LauncherImage launcherImage = null;
        if(zhihuLauncherImage != null){
            launcherImage = new LauncherImage();
            launcherImage.setImageUrl(zhihuLauncherImage.getImg());
            launcherImage.setImageText(zhihuLauncherImage.getText());
        }
        return launcherImage;
    }

    public List<Article> transform(ZhihuArticle zhihuArticle){
        List<Article> mList = null;
        boolean isToday = false;
        if(zhihuArticle != null && zhihuArticle.getTop_stories() != null
                && zhihuArticle.getTop_stories().size() > 0){
            isToday = true;
            if(mList == null){
                mList = new ArrayList<>();
            }
            Article article = new Article();
            article.setType(0);
            article.setDate(zhihuArticle.getDate());
            List<ArticleBanner> mBanner = new ArrayList<>();
            ArticleBanner articleBanner = null;
            for (ZhihuArticle.TopStoriesEntity topStoriesEntity : zhihuArticle.getTop_stories()){
                articleBanner = new ArticleBanner();
                articleBanner.setUrl(topStoriesEntity.getImage());
                articleBanner.setTitle(topStoriesEntity.getTitle());
                articleBanner.setId(String.valueOf(topStoriesEntity.getId()));
                mBanner.add(articleBanner);
            }
            article.setArticleBanners(mBanner);
            mList.add(article);
        }
        if(zhihuArticle != null && zhihuArticle.getStories() != null
                && zhihuArticle.getStories().size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }

            Article titleArticle = new Article();
            titleArticle.setType(1);
            titleArticle.setDate(zhihuArticle.getDate());
            titleArticle.setTitle(TimeUtil.converDate(zhihuArticle.getDate()));
            mList.add(titleArticle);
            if(isToday){
                titleArticle.setTitle(mContext.getResources().getString(R.string.today_list_title));
            }

            Article article = null;
            for (ZhihuArticle.StoriesEntity storiesEntity : zhihuArticle.getStories()){
                article = new Article();
                if(storiesEntity.getImages() != null && storiesEntity.getImages().size() > 0){
                    article.setImageUrl(storiesEntity.getImages().get(0));
                }
                article.setTitle(storiesEntity.getTitle());
                article.setId(String.valueOf(storiesEntity.getId()));
                article.setDate(zhihuArticle.getDate());
                article.setType(2);
                mList.add(article);
            }
        }
        return mList;
    }


    public ArticleDetail transform(ZhihuArticleDetail zhihuArticle){
        ArticleDetail articleDetail = null;
        if(zhihuArticle != null){
            articleDetail = new ArticleDetail();
            articleDetail.setId(String.valueOf(zhihuArticle.getId()));
            articleDetail.setTitle(zhihuArticle.getTitle());
            articleDetail.setContent(zhihuArticle.getBody());
            articleDetail.setImageUrl(zhihuArticle.getImage());
            articleDetail.setCss(zhihuArticle.getCss());
        }
        return articleDetail;
    }

    public Address transform(IpAddress ipAddress){
        Address address = null;
        if(ipAddress != null){
            address = new Address();
            address.setArea(ipAddress.getResult().getArea());
            address.setLocaltion(ipAddress.getResult().getLocation());
        }
        return address;
    }
}  