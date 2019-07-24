package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ArtAdBean {


    /**
     * code : 0
     * data : [{"advertising_content":"文体艺术广告位招募","advertising_id":791,"advertising_link":"文体艺术广告位招募","advertising_name":"文体艺术广告位招募","advertising_photo":"2019/01/18/1547783054523.png","advertising_time":"2019-01-18 11:44:14","advertising_type":"0","other_type":"文体艺术"},{"advertising_content":"https://www.baidu.com","advertising_id":797,"advertising_link":"博佳机器人","advertising_name":"博佳机器人","advertising_photo":"2019/01/28/1548659877493.png","advertising_time":"2019-01-28 15:17:57","advertising_type":"0","other_type":"文体艺术"}]
     * msg : success
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * advertising_content : 文体艺术广告位招募
         * advertising_id : 791
         * advertising_link : 文体艺术广告位招募
         * advertising_name : 文体艺术广告位招募
         * advertising_photo : 2019/01/18/1547783054523.png
         * advertising_time : 2019-01-18 11:44:14
         * advertising_type : 0
         * other_type : 文体艺术
         */

        private String advertising_content;
        private int advertising_id;
        private String advertising_link;
        private String advertising_name;
        private String advertising_photo;
        private String advertising_time;
        private String advertising_type;
        private String other_type;

        public String getAdvertising_content() {
            return advertising_content == null ? "" : advertising_content;
        }

        public void setAdvertising_content(String advertising_content) {
            this.advertising_content = advertising_content;
        }

        public int getAdvertising_id() {
            return advertising_id;
        }

        public void setAdvertising_id(int advertising_id) {
            this.advertising_id = advertising_id;
        }

        public String getAdvertising_link() {
            return advertising_link == null ? "" : advertising_link;
        }

        public void setAdvertising_link(String advertising_link) {
            this.advertising_link = advertising_link;
        }

        public String getAdvertising_name() {
            return advertising_name == null ? "" : advertising_name;
        }

        public void setAdvertising_name(String advertising_name) {
            this.advertising_name = advertising_name;
        }

        public String getAdvertising_photo() {
            return advertising_photo == null ? "" : advertising_photo;
        }

        public void setAdvertising_photo(String advertising_photo) {
            this.advertising_photo = advertising_photo;
        }

        public String getAdvertising_time() {
            return advertising_time == null ? "" : advertising_time;
        }

        public void setAdvertising_time(String advertising_time) {
            this.advertising_time = advertising_time;
        }

        public String getAdvertising_type() {
            return advertising_type == null ? "" : advertising_type;
        }

        public void setAdvertising_type(String advertising_type) {
            this.advertising_type = advertising_type;
        }

        public String getOther_type() {
            return other_type == null ? "" : other_type;
        }

        public void setOther_type(String other_type) {
            this.other_type = other_type;
        }
    }
}
