package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class InfoBean {

    /**
     * data : [{"help_cishu":"8","help_content":"在山的那边海的那边有一群蓝精灵","help_id":1,"help_time":"2017-09-14 15:40:17","help_title":"蓝精灵"},{"help_cishu":"2","help_content":"在山的那边海的那边有一群蓝精灵、、、、、、啊啊","help_id":2,"help_time":"2017-09-14 15:44:13","help_title":"小蓝精灵"},{"help_cishu":"1","help_content":"在山的那边海的那边有一群蓝精灵、、、、、、啊啊","help_id":3,"help_time":"2017-09-14 15:44:23","help_title":"小格格巫"},{"help_cishu":"1","help_content":"在山的那边海的那边有一群蓝精灵、、、、、、撒飒飒飒飒飒飒啊啊啊啊","help_id":4,"help_time":"2017-09-14 15:44:57","help_title":"小小蓝精"},{"help_cishu":"2","help_content":"或输入网址   ，，。设用谷歌","help_id":5,"help_time":"2017-09-18 09:42:15","help_title":"使用谷歌执行搜索"},{"help_cishu":"1","help_content":"或输入网址   ，，。设用谷歌","help_id":6,"help_time":"2017-09-18 09:42:27","help_title":"谷歌执行搜索"}]
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "help_cishu='" + help_cishu + '\'' +
                    ", help_content='" + help_content + '\'' +
                    ", help_id=" + help_id +
                    ", help_time='" + help_time + '\'' +
                    ", help_title='" + help_title + '\'' +
                    '}';
        }

        /**
         * help_cishu : 8
         * help_content : 在山的那边海的那边有一群蓝精灵
         * help_id : 1
         * help_time : 2017-09-14 15:40:17
         * help_title : 蓝精灵
         */


        private String help_cishu;
        private String help_content;
        private int help_id;
        private String help_time;
        private String help_title;

        public String getHelp_cishu() {
            return help_cishu;
        }

        public void setHelp_cishu(String help_cishu) {
            this.help_cishu = help_cishu;
        }

        public String getHelp_content() {
            return help_content;
        }

        public void setHelp_content(String help_content) {
            this.help_content = help_content;
        }

        public int getHelp_id() {
            return help_id;
        }

        public void setHelp_id(int help_id) {
            this.help_id = help_id;
        }

        public String getHelp_time() {
            return help_time;
        }

        public void setHelp_time(String help_time) {
            this.help_time = help_time;
        }

        public String getHelp_title() {
            return help_title;
        }

        public void setHelp_title(String help_title) {
            this.help_title = help_title;
        }
    }
}
