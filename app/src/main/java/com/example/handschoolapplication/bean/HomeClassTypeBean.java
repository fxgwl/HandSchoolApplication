package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HomeClassTypeBean {

    /**
     * data : [{"typeTwoInfo":[{"type_two_id":0,"type_two_name":"书画"},{"type_two_id":0,"type_two_name":"音乐"},{"type_two_id":0,"type_two_name":"球类"}],"type_one_id":"1","type_one_name":"文体艺术"},{"typeTwoInfo":[{"type_two_id":0,"type_two_name":"中小学教育"},{"type_two_id":0,"type_two_name":"课外辅导"},{"type_two_id":0,"type_two_name":"高考信息"}],"type_one_id":"2","type_one_name":"学习辅导"},{"typeTwoInfo":[{"type_two_id":0,"type_two_name":"夏令营"},{"type_two_id":0,"type_two_name":"冬令营"},{"type_two_id":0,"type_two_name":"素质训练"},{"type_two_id":0,"type_two_name":"野外生存"}],"type_one_id":"3","type_one_name":"活动拓展"},{"typeTwoInfo":[],"type_one_id":"4","type_one_name":"早教"},{"typeTwoInfo":[],"type_one_id":"5","type_one_name":"托管"},{"typeTwoInfo":[],"type_one_id":"6","type_one_name":"家教"}]
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
         * typeTwoInfo : [{"type_two_id":0,"type_two_name":"书画"},{"type_two_id":0,"type_two_name":"音乐"},{"type_two_id":0,"type_two_name":"球类"}]
         * type_one_id : 1
         * type_one_name : 文体艺术
         */

        private String type_one_id;
        private String type_one_name;
        private List<TypeTwoInfoBean> typeTwoInfo;

        public String getType_one_id() {
            return type_one_id;
        }

        public void setType_one_id(String type_one_id) {
            this.type_one_id = type_one_id;
        }

        public String getType_one_name() {
            return type_one_name;
        }

        public void setType_one_name(String type_one_name) {
            this.type_one_name = type_one_name;
        }

        public List<TypeTwoInfoBean> getTypeTwoInfo() {
            return typeTwoInfo;
        }

        public void setTypeTwoInfo(List<TypeTwoInfoBean> typeTwoInfo) {
            this.typeTwoInfo = typeTwoInfo;
        }

        public static class TypeTwoInfoBean {
            /**
             * type_two_id : 0
             * type_two_name : 书画
             */

            private int type_two_id;
            private String type_two_name;

            public int getType_two_id() {
                return type_two_id;
            }

            public void setType_two_id(int type_two_id) {
                this.type_two_id = type_two_id;
            }

            public String getType_two_name() {
                return type_two_name;
            }

            public void setType_two_name(String type_two_name) {
                this.type_two_name = type_two_name;
            }
        }
    }
}
