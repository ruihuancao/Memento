package com.memento.android.data.entity;

import java.util.List;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 17:21
 * 描述：
 * 修改历史：
 */
public class TvCategoryEntity {

    /**
     * error_code : 0
     * reason : 请求成功！
     * result : [{"id":1,"name":"央视"},{"id":2,"name":"卫视"},{"id":3,"name":"数字"},{"id":4,"name":"城市"},{"id":5,"name":"CETV"},{"id":6,"name":"原创"}]
     */

    private int error_code;
    private String reason;
    /**
     * id : 1
     * name : 央视
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
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}