package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class SignBean {


    /**
     * data : [{"date_time":"2017-11-09 09:03:03","sign_city":"天津市","sign_id":46,"sign_time":"2017-11-09","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"date_time":"2017-11-08 19:09:06","sign_city":"天津市","sign_id":45,"sign_time":"2017-11-08","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"date_time":"2017-11-07 13:59:12","sign_city":"天津市","sign_id":42,"sign_time":"2017-11-07","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"date_time":"2017-11-06 15:46:26","sign_city":"天津市","sign_id":41,"sign_time":"2017-11-06","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"date_time":"2017-11-04 15:59:35","sign_city":"天津市","sign_id":38,"sign_time":"2017-11-04","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"date_time":"2017-11-03 15:15:36","sign_city":"天津市","sign_id":36,"sign_time":"2017-11-03","sign_type":"0","user_id":"772d42a5a0014faa8706f7cd3f6e8828"}]
     * msg : 成功
     * result : 0
     */

    private String msg;
    private int result;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date_time : 2017-11-09 09:03:03
         * sign_city : 天津市
         * sign_id : 46
         * sign_time : 2017-11-09
         * sign_type : 0
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
         */

        private String date_time;
        private String sign_city;
        private int sign_id;
        private String sign_time;
        private String sign_type;
        private String user_id;

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getSign_city() {
            return sign_city;
        }

        public void setSign_city(String sign_city) {
            this.sign_city = sign_city;
        }

        public int getSign_id() {
            return sign_id;
        }

        public void setSign_id(int sign_id) {
            this.sign_id = sign_id;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
