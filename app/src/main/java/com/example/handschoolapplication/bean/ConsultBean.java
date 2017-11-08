package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class ConsultBean {


    /**
     * data : [{"artificial_id":14,"message_content":"哈哈哈","message_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg","message_time":"2017-09-19 15:35:41","message_type":"用户","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_phone":"15122947309"},{"artificial_id":15,"message_content":"123","message_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg","message_time":"2017-09-19 15:44:00","message_type":"用户","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_phone":"15122947309"},{"artificial_id":20,"message_content":"Adds","message_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg","message_time":"2017-09-25 14:31:46","message_type":"用户","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_phone":"15122947309"},{"artificial_id":21,"message_content":"W","message_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg","message_time":"2017-09-27 15:01:59","message_type":"用户","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_phone":"15122947309"},{"artificial_id":37,"message_content":"a","message_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg","message_time":"2017-10-24 13:52:26","message_type":"用户","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_phone":"15122947309"}]
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
         * artificial_id : 14
         * message_content : 哈哈哈
         * message_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1510106406283.jpg
         * message_time : 2017-09-19 15:35:41
         * message_type : 用户
         * user_id : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_phone : 15122947309
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
