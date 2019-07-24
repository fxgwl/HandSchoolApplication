package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HomeAdBean {

    /**
     * data : [{"advertising_id":0,"advertising_link":"aaa","advertising_name":"一一","advertising_phone":"111","advertising_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605983.jpg","advertising_time":"2017-09-19 17:29:57","advertising_type":"a"},{"advertising_id":0,"advertising_link":"bbb","advertising_name":"二二","advertising_phone":"111","advertising_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605983.jpg","advertising_time":"2017-09-19 17:30:35","advertising_type":"a"}]
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
         * advertising_id : 0
         * advertising_link : aaa
         * advertising_name : 一一
         * advertising_phone : 111
         * advertising_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605983.jpg
         * advertising_time : 2017-09-19 17:29:57
         * advertising_type : a
         */

        private int advertising_id;
        private String advertising_link;
        private String advertising_name;
        private String advertising_content;
        private String advertising_photo;
        private String advertising_time;
        private String advertising_type;

        public int getAdvertising_id() {
            return advertising_id;
        }

        public void setAdvertising_id(int advertising_id) {
            this.advertising_id = advertising_id;
        }

        public String getAdvertising_link() {
            return advertising_link;
        }

        public void setAdvertising_link(String advertising_link) {
            this.advertising_link = advertising_link;
        }

        public String getAdvertising_name() {
            return advertising_name;
        }

        public void setAdvertising_name(String advertising_name) {
            this.advertising_name = advertising_name;
        }

        public String getAdvertising_phone() {
            return advertising_content;
        }

        public void setAdvertising_phone(String advertising_content) {
            this.advertising_content = advertising_content;
        }

        public String getAdvertising_photo() {
            return advertising_photo;
        }

        public void setAdvertising_photo(String advertising_photo) {
            this.advertising_photo = advertising_photo;
        }

        public String getAdvertising_time() {
            return advertising_time;
        }

        public void setAdvertising_time(String advertising_time) {
            this.advertising_time = advertising_time;
        }

        public String getAdvertising_type() {
            return advertising_type;
        }

        public void setAdvertising_type(String advertising_type) {
            this.advertising_type = advertising_type;
        }

        public String getAdvertising_content() {
            return advertising_content;
        }

        public void setAdvertising_content(String advertising_content) {
            this.advertising_content = advertising_content;
        }
    }
}
