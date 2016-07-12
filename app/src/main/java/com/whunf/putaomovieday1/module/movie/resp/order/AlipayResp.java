package com.whunf.putaomovieday1.module.movie.resp.order;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;

/**
 * Created by xiaohui on 16/7/11.
 */
public class AlipayResp extends BaseResp {
//    {
//        "ret_code": "0000",
//            "msg": "",
//            "data": {
//        "sign": "UqrBTu8Q96Qfar09aNU0nGCOLspqZAsK4YsqrCiZKq9uVSBecOGybAeKu3bpva6fVX43rF/flAn4AsGr8EpRYudGqD5V2G3Cz2ChJyv+Qz844puzdVN3ZT9cnxBkWx2oH/S4geU1gKJLYQOEWCXg9ce4AFN3ETs0iivi6M8Yu1A=",
//                "service": "mobile.securitypay.pay",
//                "partner": "2088511630372730",
//                "notify_url": "http://121.40.186.18:5180/pay/notify/alipay",
//                "out_trade_no": "MOVIE-249219581-16534",
//                "subject": "深圳市星际银河坑梓电影院电影票",
//                "payment_type": 1,
//                "seller_id": "hl@putao.cn",
//                "total_fee": "38.0",
//                "body": "深圳市星际银河坑梓电影院电影票",
//                "return_url": null
//    }
//    }

    private AlipayBean data;

    public AlipayBean getData() {
        return data;
    }

    public void setData(AlipayBean data) {
        this.data = data;
    }

    public static class AlipayBean {

        private String sign;
        private String service;
        private String partner;
        private String notify_url;
        private String out_trade_no;
        private String subject;
        private int payment_type;
        private String seller_id;
        private String total_fee;
        private String body;
        private String return_url;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(int payment_type) {
            this.payment_type = payment_type;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getReturn_url() {
            return return_url;
        }

        public void setReturn_url(String return_url) {
            this.return_url = return_url;
        }

        @Override
        public String toString() {
            return "AlipayBean{" +
                    "sign='" + sign + '\'' +
                    ", service='" + service + '\'' +
                    ", partner='" + partner + '\'' +
                    ", notify_url='" + notify_url + '\'' +
                    ", out_trade_no='" + out_trade_no + '\'' +
                    ", subject='" + subject + '\'' +
                    ", payment_type=" + payment_type +
                    ", seller_id='" + seller_id + '\'' +
                    ", total_fee='" + total_fee + '\'' +
                    ", body='" + body + '\'' +
                    ", return_url='" + return_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AlipayResp{" +
                "data=" + data +
                '}';
    }
}
