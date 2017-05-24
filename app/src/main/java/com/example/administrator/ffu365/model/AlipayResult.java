package com.example.administrator.ffu365.model;

/**
 * Created by 曾辉 on 2016/8/30.
 * Email : 240336124@qq.com
 * Description :
 */
public class AlipayResult {

    /**
     * payinfo : _input_charset="utf-8"&app_id="1"&it_b_pay="15d"&notify_url="http://v2.ffu365.com/index.php/Api/Gateway/aliPayGateway.html"&out_trade_no="20160830105716595317"&partner="2088612417094800"&payment_type="1"&seller_id="3185016348@qq.com"&service="mobile.securitypay.pay"&subject="金币充值"&total_fee="0.1"&sign="hlhGKGNLCzH0%2BSLit8obisMR0NlWI0dcN%2Bd7%2FvcMUG2%2BYNq%2FknKRieo5NU6OvOTFAv5KnaMVpSf8lnBXLuwm6aNuC%2FX%2BE6tlTPVN4I0KyKgAfgMzMHVbByY5%2FOczinr1eGUzSjgTH6Yc2ESliEs08Rd55HHIHA9q%2B5ZXWc5A0hw%3D"&sign_type="RSA"
     */

    private DataBean data;
    /**
     * data : {"payinfo":"_input_charset=\"utf-8\"&app_id=\"1\"&it_b_pay=\"15d\"&notify_url=\"http://v2.ffu365.com/index.php/Api/Gateway/aliPayGateway.html\"&out_trade_no=\"20160830105716595317\"&partner=\"2088612417094800\"&payment_type=\"1\"&seller_id=\"3185016348@qq.com\"&service=\"mobile.securitypay.pay\"&subject=\"金币充值\"&total_fee=\"0.1\"&sign=\"hlhGKGNLCzH0%2BSLit8obisMR0NlWI0dcN%2Bd7%2FvcMUG2%2BYNq%2FknKRieo5NU6OvOTFAv5KnaMVpSf8lnBXLuwm6aNuC%2FX%2BE6tlTPVN4I0KyKgAfgMzMHVbByY5%2FOczinr1eGUzSjgTH6Yc2ESliEs08Rd55HHIHA9q%2B5ZXWc5A0hw%3D\"&sign_type=\"RSA\""}
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
        private String payinfo;

        public String getPayinfo() {
            return payinfo;
        }

        public void setPayinfo(String payinfo) {
            this.payinfo = payinfo;
        }
    }
}
