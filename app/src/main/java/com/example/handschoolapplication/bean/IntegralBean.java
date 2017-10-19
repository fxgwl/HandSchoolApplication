package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class IntegralBean {

    /**
     * data : [{"integral_cause":"0","integral_id":6,"integral_num":"10","integral_time":"2017-10-16 14:56:37","user_id":"205c2f34bc064712bf2e0157c142b6dd"}]
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
         * integral_cause : 0
         * integral_id : 6
         * integral_num : 10
         * integral_time : 2017-10-16 14:56:37
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         */

        private String integral_cause;
        private int integral_id;
        private String integral_num;
        private String integral_time;
        private String user_id;

        public String getIntegral_cause() {
            return integral_cause;
        }

        public void setIntegral_cause(String integral_cause) {
            this.integral_cause = integral_cause;
        }

        public int getIntegral_id() {
            return integral_id;
        }

        public void setIntegral_id(int integral_id) {
            this.integral_id = integral_id;
        }

        public String getIntegral_num() {
            return integral_num;
        }

        public void setIntegral_num(String integral_num) {
            this.integral_num = integral_num;
        }

        public String getIntegral_time() {
            return integral_time;
        }

        public void setIntegral_time(String integral_time) {
            this.integral_time = integral_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
