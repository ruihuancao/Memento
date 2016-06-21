package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 17:25
 * 描述：
 * 修改历史：
 */
public class TvList {


    /**
     * error_code : 0
     * reason : Success!
     * result : [{"channelName":"CCTV-1 综合","pId":1,"rel":"cctv1","url":"http://tv.cntv.cn/live/cctv1"},{"channelName":"CCTV-2 财经","pId":1,"rel":"cctv2","url":"http://tv.cntv.cn/live/cctv2"},{"channelName":"CCTV-3 综艺","pId":1,"rel":"cctv3","url":"http://tv.cntv.cn/live/cctv3"},{"channelName":"CCTV-4 (亚洲)","pId":1,"rel":"cctv4","url":"http://tv.cntv.cn/live/cctv4"},{"channelName":"CCTV-4 (欧洲)","pId":1,"rel":"cctveurope","url":"http://tv.cntv.cn/live/cctveurope"},{"channelName":"CCTV-4 (美洲)","pId":1,"rel":"cctvamerica","url":"http://tv.cntv.cn/live/cctvamerica"},{"channelName":"CCTV-5 体育","pId":1,"rel":"cctv5","url":"http://tv.cntv.cn/live/cctv5"},{"channelName":"CCTV-6 电影","pId":1,"rel":"cctv6","url":"http://tv.cntv.cn/live/cctv6"},{"channelName":"CCTV-7 军事农业","pId":1,"rel":"cctv7","url":"http://tv.cntv.cn/live/cctv7"},{"channelName":"CCTV-8 电视剧","pId":1,"rel":"cctv8","url":"http://tv.cntv.cn/live/cctv8"},{"channelName":"CCTV-9 纪录","pId":1,"rel":"cctvjilu","url":"http://tv.cntv.cn/live/cctvjilu"},{"channelName":"CCTV-9 纪录(英)","pId":1,"rel":"cctvdoc","url":"http://tv.cntv.cn/live/cctvdoc"},{"channelName":"CCTV-10 科教","pId":1,"rel":"cctv10","url":"http://tv.cntv.cn/live/cctv10"},{"channelName":"CCTV-11 戏曲","pId":1,"rel":"cctv11","url":"http://tv.cntv.cn/live/cctv11"},{"channelName":"CCTV-12 社会与法","pId":1,"rel":"cctv12","url":"http://tv.cntv.cn/live/cctv12"},{"channelName":"CCTV-13 新闻","pId":1,"rel":"cctv13","url":"http://tv.cntv.cn/live/cctv13"},{"channelName":"CCTV-14 少儿","pId":1,"rel":"cctvchild","url":"http://tv.cntv.cn/live/cctvchild"},{"channelName":"CCTV-15 音乐","pId":1,"rel":"cctv15","url":"http://tv.cntv.cn/live/cctv15"},{"channelName":"CCTV-NEWS","pId":1,"rel":"cctv9","url":"http://tv.cntv.cn/live/cctv9"},{"channelName":"CCTV-Français","pId":1,"rel":"cctvfrench","url":"http://tv.cntv.cn/live/cctvfrench"},{"channelName":"CCTV-Español","pId":1,"rel":"cctvxiyu","url":"http://tv.cntv.cn/live/cctvxiyu"},{"channelName":"CCTV-العربية","pId":1,"rel":"cctvarabic","url":"http://tv.cntv.cn/live/cctvarabic"},{"channelName":"CCTV-Русский","pId":1,"rel":"cctvrussian","url":"http://tv.cntv.cn/live/cctvrussian"},{"channelName":"CCTV体育赛事","pId":1,"rel":"cctv5plus","url":"http://tv.cntv.cn/live/cctv5plus"}]
     */

    private int error_code;
    private String reason;
    /**
     * channelName : CCTV-1 综合
     * pId : 1
     * rel : cctv1
     * url : http://tv.cntv.cn/live/cctv1
     */

    private List<ResultEntity> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String channelName;
        private int pId;
        private String rel;
        private String url;

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}