package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class TeachNewsBean {

    /**
     * data : [{"news_content":"北冥有鱼，其名为鲲，鲲之大，不知其几千里也，化而为鸟，其名为鹏","news_id":1,"news_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","news_record":"1","news_time":"2017-09-19 15:44:31","news_title":"逍遥游","news_type":"教育"},{"news_content":"北冥有鱼，其名为鲲，鲲之大，不知其几千里也，化而为鸟，其名为鹏","news_id":2,"news_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","news_time":"2017-09-19 17:02:55","news_title":"逍遥游游","news_type":"教育"},{"news_content":"北冥有鱼，其名为鲲，鲲之大，不知其几千里也，化而为鸟，其名为鹏","news_id":3,"news_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","news_time":"2017-09-19 17:03:23","news_title":"逍遥游游游","news_type":"资讯"},{"news_content":"北冥有鱼，其名为鲲，鲲之大，不知其几千里也，化而为鸟，其名为鹏","news_id":4,"news_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","news_time":"2017-09-19 17:03:57","news_title":"逍遥游一游","news_type":"资讯"}]
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
         * news_content : 北冥有鱼，其名为鲲，鲲之大，不知其几千里也，化而为鸟，其名为鹏
         * news_id : 1
         * news_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg
         * news_record : 1
         * news_time : 2017-09-19 15:44:31
         * news_title : 逍遥游
         * news_type : 教育
         */

        private String news_content;
        private int news_id;
        private String news_photo;
        private String news_record;
        private String news_time;
        private String news_title;
        private String news_type;

        public String getNews_content() {
            return news_content;
        }

        public void setNews_content(String news_content) {
            this.news_content = news_content;
        }

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getNews_photo() {
            return news_photo;
        }

        public void setNews_photo(String news_photo) {
            this.news_photo = news_photo;
        }

        public String getNews_record() {
            return news_record;
        }

        public void setNews_record(String news_record) {
            this.news_record = news_record;
        }

        public String getNews_time() {
            return news_time;
        }

        public void setNews_time(String news_time) {
            this.news_time = news_time;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }
    }
}
