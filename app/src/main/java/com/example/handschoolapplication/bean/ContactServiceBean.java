package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ContactServiceBean {

    /**
     * data : [{"artificial_id":24,"message_content":"阿凡达发放","message_photo":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507603975481.jpg","message_time":"2017-10-11 10:14:02","message_type":"用户","user_id":"ba28cc5ec0c449098285583a5f1efc4d","user_phone":"2"}]
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
         * artificial_id : 24
         * message_content : 阿凡达发放
         * message_photo : bicths/ba28cc5ec0c449098285583a5f1efc4d/1507603975481.jpg
         * message_time : 2017-10-11 10:14:02
         * message_type : 用户
         * user_id : ba28cc5ec0c449098285583a5f1efc4d
         * user_phone : 2
         */

        private int artificial_id;
        private String message_content;
        private String message_photo;
        private String message_time;
        private String message_type;
        private String user_id;
        private String user_phone;

        public int getArtificial_id() {
            return artificial_id;
        }

        public void setArtificial_id(int artificial_id) {
            this.artificial_id = artificial_id;
        }

        public String getMessage_content() {
            return message_content;
        }

        public void setMessage_content(String message_content) {
            this.message_content = message_content;
        }

        public String getMessage_photo() {
            return message_photo;
        }

        public void setMessage_photo(String message_photo) {
            this.message_photo = message_photo;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }
    }
}
