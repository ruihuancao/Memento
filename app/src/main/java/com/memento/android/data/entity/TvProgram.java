package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 17:28
 * 描述：
 * 修改历史：
 */
public class TvProgram {

    /**
     * error_code : 0
     * reason : Success!
     * result : [{"cName":"CCTV-1 综合","pName":"生活提示","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=0","time":"2016-05-06 00:14"},{"cName":"CCTV-1 综合","pName":"动物世界","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=1","time":"2016-05-06 00:26"},{"cName":"CCTV-1 综合","pName":"晚间新闻（重播）","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=2","time":"2016-05-06 01:01"},{"cName":"CCTV-1 综合","pName":"精彩一刻-普法144","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=3","time":"2016-05-06 01:33"},{"cName":"CCTV-1 综合","pName":"精彩一刻-普法145","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=4","time":"2016-05-06 02:15"},{"cName":"CCTV-1 综合","pName":"精彩一刻-春晚歌曲10","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=5","time":"2016-05-06 02:57"},{"cName":"CCTV-1 综合","pName":"2014出彩中国人","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=6","time":"2016-05-06 03:01"},{"cName":"CCTV-1 综合","pName":"今日说法","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=7","time":"2016-05-06 04:31"},{"cName":"CCTV-1 综合","pName":"新闻联播","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=8","time":"2016-05-06 04:59"},{"cName":"CCTV-1 综合","pName":"人与自然","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=9","time":"2016-05-06 05:29"},{"cName":"CCTV-1 综合","pName":"朝闻天下","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=10","time":"2016-05-06 06:00"},{"cName":"CCTV-1 综合","pName":"生活早参考-特别节目（生活圈）2016-93","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=11","time":"2016-05-06 08:35"},{"cName":"CCTV-1 综合","pName":"电视剧：穆桂英挂帅35/39","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=12","time":"2016-05-06 09:26"},{"cName":"CCTV-1 综合","pName":"电视剧：穆桂英挂帅36/39","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=13","time":"2016-05-06 10:16"},{"cName":"CCTV-1 综合","pName":"电视剧：穆桂英挂帅37/39","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=14","time":"2016-05-06 11:06"},{"cName":"CCTV-1 综合","pName":"新闻30分","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=15","time":"2016-05-06 12:00"},{"cName":"CCTV-1 综合","pName":"今日说法","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=16","time":"2016-05-06 12:35"},{"cName":"CCTV-1 综合","pName":"精彩一刻-普法146","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=17","time":"2016-05-06 13:11"},{"cName":"CCTV-1 综合","pName":"精彩一刻-普法147","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=18","time":"2016-05-06 13:53"},{"cName":"CCTV-1 综合","pName":"电视剧：遥远的婚约20/37","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=19","time":"2016-05-06 14:43"},{"cName":"CCTV-1 综合","pName":"电视剧：遥远的婚约21/37","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=20","time":"2016-05-06 15:32"},{"cName":"CCTV-1 综合","pName":"2015中国梦-揣着梦想走四方","pUrl":"http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=21","time":"2016-05-06 16:25"},{"cName":"CCTV-1 综合","pName":"第一动画乐园","pUrl":"http://tv.cntv.cn/live/cctv1","time":"2016-05-06 16:35"},{"cName":"CCTV-1 综合","pName":"第一动画乐园","pUrl":"","time":"2016-05-06 17:13"},{"cName":"CCTV-1 综合","pName":"第一动画乐园","pUrl":"","time":"2016-05-06 17:54"},{"cName":"CCTV-1 综合","pName":"新闻联播","pUrl":"","time":"2016-05-06 18:59"},{"cName":"CCTV-1 综合","pName":"焦点访谈","pUrl":"","time":"2016-05-06 19:37"},{"cName":"CCTV-1 综合","pName":"前情提要《父亲的身份》18/36","pUrl":"","time":"2016-05-06 19:59"},{"cName":"CCTV-1 综合","pName":"电视剧：父亲的身份18/36","pUrl":"","time":"2016-05-06 20:02"},{"cName":"CCTV-1 综合","pName":"前情提要《父亲的身份》19/36","pUrl":"","time":"2016-05-06 20:54"},{"cName":"CCTV-1 综合","pName":"电视剧：父亲的身份19/36","pUrl":"","time":"2016-05-06 20:56"},{"cName":"CCTV-1 综合","pName":"精彩一刻-手艺臭味香投14","pUrl":"","time":"2016-05-06 21:46"},{"cName":"CCTV-1 综合","pName":"晚间新闻","pUrl":"","time":"2016-05-06 22:00"},{"cName":"CCTV-1 综合","pName":"吉尼斯中国之夜","pUrl":"","time":"2016-05-06 22:35"},{"cName":"CCTV-1 综合","pName":"生活提示","pUrl":"","time":"2016-05-06 23:45"},{"cName":"CCTV-1 综合","pName":"国际艺苑（香港）","pUrl":"","time":"2016-05-06 23:49"}]
     */

    private int error_code;
    private String reason;
    /**
     * cName : CCTV-1 综合
     * pName : 生活提示
     * pUrl : http://tv.cntv.cn/live/cctv1?date=2016-05-06&index=0
     * time : 2016-05-06 00:14
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
        private String cName;
        private String pName;
        private String pUrl;
        private String time;

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPUrl() {
            return pUrl;
        }

        public void setPUrl(String pUrl) {
            this.pUrl = pUrl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}