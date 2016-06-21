package com.memento.android.data.entity;

import java.util.List;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-02-16
 * Time: 11:44
 *
 */
public class ZhihuArticle {

    /**
     * date : 20160401
     * stories : [{"images":["http://pic4.zhimg.com/b6396dbb89b42f41015a08584dda54cb.jpg"],"type":0,"id":8086925,"ga_prefix":"040116","title":"为什么富人也喜欢借钱？"},{"images":["http://pic3.zhimg.com/de1a6a6e065e56aa94e857c47a92bb4a.jpg"],"type":0,"id":8083848,"ga_prefix":"040115","title":"电影里被车、被人、被门一撞就失忆，现实还真不容易"},{"images":["http://pic1.zhimg.com/6b0f6a87cc5123ad6978e18004f6918c.jpg"],"type":0,"id":8086629,"ga_prefix":"040114","title":"为了能愉快地交配，植物们也是想尽了办法"},{"images":["http://pic3.zhimg.com/3140db2667ab6e187e12e6f276cbf8c2.jpg"],"type":0,"id":8083992,"ga_prefix":"040113","title":"一份不负春光的日式早餐，新手也能搞定"},{"images":["http://pic2.zhimg.com/2c500633372a1b152d9f977849cf5a05.jpg"],"type":0,"id":8075042,"ga_prefix":"040112","title":"关于江苏区域经济发展不平衡的现象，我做了个研究"},{"images":["http://pic1.zhimg.com/485984533843893994144dcf6d8cdef4.jpg"],"type":0,"id":8086788,"ga_prefix":"040111","title":"如何摆脱抑郁？"},{"images":["http://pic3.zhimg.com/6ecb2d6243dd65afc855bb616269e79e.jpg"],"type":0,"id":8081499,"ga_prefix":"040110","title":"排队（搭讪）技巧一则"},{"title":"去《Legal High》古美门律师事务所吃法餐","ga_prefix":"040109","images":["http://pic1.zhimg.com/1205cbbcaed4c53eb2138eb6256df540.jpg"],"multipic":true,"type":0,"id":8082626},{"images":["http://pic4.zhimg.com/683ea62eab6830c4889c78b7511238cb.jpg"],"type":0,"id":8084347,"ga_prefix":"040108","title":"「摄影不就是按个快门吗，怎么就成艺术了？」"},{"title":"一代女建筑大师去世了，这是她设计的望京 SOHO","ga_prefix":"040107","images":["http://pic4.zhimg.com/3c82bd9e0f13217e56550c3d8897e7a3.jpg"],"multipic":true,"type":0,"id":8085203},{"images":["http://pic1.zhimg.com/f1ad03a469f83c2026e16eb01e3a07ac.jpg"],"type":0,"id":8082616,"ga_prefix":"040107","title":"几乎所有人都在围观，却少有人出手相助"},{"images":["http://pic4.zhimg.com/0a89a1357f3285fa94f96cb332dfd41f.jpg"],"type":0,"id":8065407,"ga_prefix":"040107","title":"别只谈大龄女性，大龄男性生娃也有风险"},{"images":["http://pic2.zhimg.com/c27ac1c169724fc9a5fbb5263ed96e59.jpg"],"type":0,"id":8085143,"ga_prefix":"040107","title":"读读日报 24 小时热门 TOP 5 · 柳岩当伴娘，全程找不到绅士"},{"images":["http://pic2.zhimg.com/3193da87a94a51071bffc1f718b01265.jpg"],"type":0,"id":8064572,"ga_prefix":"040106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/cb97b0027a2037e133a7c219b5021a40.jpg","type":0,"id":8086788,"ga_prefix":"040111","title":"如何摆脱抑郁？"},{"image":"http://pic3.zhimg.com/fed6ffa2444d4e0ac6fdc6336226964e.jpg","type":0,"id":8085203,"ga_prefix":"040107","title":"一代女建筑大师去世了，这是她设计的望京 SOHO"},{"image":"http://pic3.zhimg.com/b8079ec2d7f638b5848effc1a4b8631a.jpg","type":0,"id":8085143,"ga_prefix":"040107","title":"读读日报 24 小时热门 TOP 5 · 柳岩当伴娘，全程找不到绅士"},{"image":"http://pic3.zhimg.com/09796e9eb366d974d6a0a2caff81da36.jpg","type":0,"id":8082626,"ga_prefix":"040109","title":"去《Legal High》古美门律师事务所吃法餐"},{"image":"http://pic4.zhimg.com/86cd70da7f73fdac0e73c7b559aa4d13.jpg","type":0,"id":8082616,"ga_prefix":"040107","title":"几乎所有人都在围观，却少有人出手相助"}]
     */

    private String date;
    /**
     * images : ["http://pic4.zhimg.com/b6396dbb89b42f41015a08584dda54cb.jpg"]
     * type : 0
     * id : 8086925
     * ga_prefix : 040116
     * title : 为什么富人也喜欢借钱？
     */

    private List<StoriesEntity> stories;
    /**
     * image : http://pic1.zhimg.com/cb97b0027a2037e133a7c219b5021a40.jpg
     * type : 0
     * id : 8086788
     * ga_prefix : 040111
     * title : 如何摆脱抑郁？
     */

    private List<TopStoriesEntity> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesEntity {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesEntity {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}