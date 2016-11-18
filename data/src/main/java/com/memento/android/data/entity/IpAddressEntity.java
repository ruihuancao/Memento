package com.memento.android.data.entity;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-05-06
 * 时间: 16:02
 * 描述：
 * 修改历史：
 */
public class IpAddressEntity {

    /**
     * resultcode : 200
     * reason : Return Successd!
     * result : {"area":"北京市","location":"北京百度网讯科技有限公司BGP节点"}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * area : 北京市
     * location : 北京百度网讯科技有限公司BGP节点
     */

    private ResultEntity result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultEntity {
        private String area;
        private String location;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}