package com.example.administrator.ffu365.model;

/**
 * Created by 曾辉 on 2016/8/30.
 * Email : 240336124@qq.com
 * Description :
 */
public class WXPayResult {

    /**
     * payinfo : {"appid":"wxa8080d15a32e2ff7","noncestr":"G49SpYuX0DoiJDQsKk7D0GnU1IqJ17ip","partnerid":"1270695501","prepayid":"wx2016083011130430448b196f0431480890","timestamp":1472526784,"sign":"AFC755772D9D31024B2F52BD1C5714AA","package_value":"Sign=WXPay"}
     */

    private DataBean data;
    /**
     * data : {"payinfo":{"appid":"wxa8080d15a32e2ff7","noncestr":"G49SpYuX0DoiJDQsKk7D0GnU1IqJ17ip","partnerid":"1270695501","prepayid":"wx2016083011130430448b196f0431480890","timestamp":1472526784,"sign":"AFC755772D9D31024B2F52BD1C5714AA","package_value":"Sign=WXPay"}}
     * errcode : 1
     * errmsg : 操作成功
     * errdialog : 0
     */

    private int errcode;
    private String errmsg;
    private int errdialog;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrdialog() {
        return errdialog;
    }

    public void setErrdialog(int errdialog) {
        this.errdialog = errdialog;
    }

    public static class DataBean {
        /**
         * appid : wxa8080d15a32e2ff7
         * noncestr : G49SpYuX0DoiJDQsKk7D0GnU1IqJ17ip
         * partnerid : 1270695501
         * prepayid : wx2016083011130430448b196f0431480890
         * timestamp : 1472526784
         * sign : AFC755772D9D31024B2F52BD1C5714AA
         * package_value : Sign=WXPay
         */

        private PayinfoBean payinfo;

        public PayinfoBean getPayinfo() {
            return payinfo;
        }

        public void setPayinfo(PayinfoBean payinfo) {
            this.payinfo = payinfo;
        }

        public static class PayinfoBean {
            private String appid;
            private String noncestr;
            private String partnerid;
            private String prepayid;
            private int timestamp;
            private String sign;
            private String package_value;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPackage_value() {
                return package_value;
            }

            public void setPackage_value(String package_value) {
                this.package_value = package_value;
            }
        }
    }
}
