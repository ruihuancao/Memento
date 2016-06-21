package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 16:55
 * 描述：
 * 修改历史：
 */
public class Joke {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"一女人来到男人家玩，女人调戏男人说：\u201c你要是有块地我就和你结婚。\u201d\r\n这时，门铃响了，快递小哥说：\u201c先生，有你快递！\u201d","hashId":"ab37e1fe35eb3d49af5c2166dd45eafa","unixtime":1462524832,"updatetime":"2016-05-06 16:53:52"},{"content":"原来以为在大学里篮球比赛是这样的\u2014\u2014球场边有很多妹子，激动地喊：学长加油学弟最棒之类的。\r\n但事实往往是这样的\u2014\u2014群学长站在场边，大喊：sb！快传啊！sb！投篮啊！sb！别空人啊！","hashId":"031b559ca343379bd5ba586f48cac162","unixtime":1462524832,"updatetime":"2016-05-06 16:53:52"},{"content":"和往常一样，父亲喝了点酒，一场家庭暴力即将发生。\r\n我忍不住问他：\u201c爸！你为什么要喝这么多酒？！\u201d\r\n父亲深深的吸了一口烟，忧郁地说：\u201c麻醉了全身，挨你妈打时不会那么疼。\u201d","hashId":"ff8f72c79a692a020145ae24e8557fe5","unixtime":1462524832,"updatetime":"2016-05-06 16:53:52"}]}
     */

    private int error_code;
    private String reason;
    private ResultEntity result;

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

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * content : 一女人来到男人家玩，女人调戏男人说：“你要是有块地我就和你结婚。”
         这时，门铃响了，快递小哥说：“先生，有你快递！”
         * hashId : ab37e1fe35eb3d49af5c2166dd45eafa
         * unixtime : 1462524832
         * updatetime : 2016-05-06 16:53:52
         */

        private List<DataEntity> data;

        public List<DataEntity> getData() {
            return data;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public static class DataEntity {
            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}