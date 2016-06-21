package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 17:09
 * 描述：
 * 修改历史：
 */
public class WxArticleQuery {


    /**
     * reason : success
     * result : {"list":[{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062197.jpg/640","id":"wechat_20160506059312","source":"关注国产手机","title":"【爆料】老罗亲自爆料：锤子T3配置信息亮相","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059312","mark":""},{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062199.jpg/640","id":"wechat_20160506059313","source":"关注国产手机","title":"【行情】iPhone商标归中国公司！苹果愤怒回应","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059313","mark":""},{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062200.jpg/640","id":"wechat_20160506059314","source":"关注国产手机","title":"【榜单】4月性价比手机排行榜","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059314","mark":""}],"totalPage":167,"ps":3,"pno":1}
     * error_code : 0
     */

    private String reason;
    /**
     * list : [{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062197.jpg/640","id":"wechat_20160506059312","source":"关注国产手机","title":"【爆料】老罗亲自爆料：锤子T3配置信息亮相","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059312","mark":""},{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062199.jpg/640","id":"wechat_20160506059313","source":"关注国产手机","title":"【行情】iPhone商标归中国公司！苹果愤怒回应","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059313","mark":""},{"firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062200.jpg/640","id":"wechat_20160506059314","source":"关注国产手机","title":"【榜单】4月性价比手机排行榜","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059314","mark":""}]
     * totalPage : 167
     * ps : 3
     * pno : 1
     */

    private ResultEntity result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultEntity {
        private int totalPage;
        private int ps;
        private int pno;
        /**
         * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-5062197.jpg/640
         * id : wechat_20160506059312
         * source : 关注国产手机
         * title : 【爆料】老罗亲自爆料：锤子T3配置信息亮相
         * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20160506059312
         * mark :
         */

        private List<ListEntity> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            private String firstImg;
            private String id;
            private String source;
            private String title;
            private String url;
            private String mark;

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }
        }
    }
}