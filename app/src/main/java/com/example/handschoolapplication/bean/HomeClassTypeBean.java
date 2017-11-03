package com.example.handschoolapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HomeClassTypeBean implements Serializable{


    /**
     * data : [{"only_num":"2017/10/28/1509185126042.jpg","typeTwoInfo":[{"one_num":"1","type_one_name":"文体艺术","type_two_id":1,"type_two_name":"书画"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":2,"type_two_name":"音乐"},{"one_num":"1","typeThreeInfo":[{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":1,"type_three_name":"足球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":2,"type_three_name":"篮球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":4,"type_three_name":"乒乓球"}],"type_one_name":"文体艺术","type_two_id":3,"type_two_name":"球类"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":11,"type_two_name":"红烧肉"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":12,"type_two_name":"小炖肉"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":13,"type_two_name":"酱肘子"}],"type_one_id":"1","type_one_name":"文体艺术"},{"only_num":"2017/10/28/1509185156422.jpg","typeTwoInfo":[{"one_num":"2","typeThreeInfo":[{"one_name":"学习辅导","one_num":2,"two_name":"中小学教育","two_num":4,"type_three_id":5,"type_three_name":"数学"}],"type_one_name":"学习辅导","type_two_id":4,"type_two_name":"中小学教育"},{"one_num":"2","type_one_name":"学习辅导","type_two_id":5,"type_two_name":"课外辅导"},{"one_num":"2","type_one_name":"学习辅导","type_two_id":6,"type_two_name":"高考信息"}],"type_one_id":"2","type_one_name":"学习辅导"},{"only_num":"2017/10/28/1509185174359.jpg","typeTwoInfo":[{"one_num":"3","type_one_name":"活动拓展","type_two_id":7,"type_two_name":"夏令营"},{"one_num":"3","type_one_name":"活动拓展","type_two_id":8,"type_two_name":"冬令营"},{"one_num":"3","type_one_name":"活动拓展","type_two_id":9,"type_two_name":"素质训练"},{"one_num":"3","type_one_name":"活动拓展","type_two_id":10,"type_two_name":"野外生存"}],"type_one_id":"3","type_one_name":"活动拓展"},{"only_num":"2017/10/28/1509185193900.jpg","type_one_id":"4","type_one_name":"早教"},{"only_num":"2017/10/28/1509185220045.jpg","type_one_id":"5","type_one_name":"托教"},{"only_num":"2017/10/28/1509185240028.jpg","type_one_id":"6","type_one_name":"家教"}]
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
         * only_num : 2017/10/28/1509185126042.jpg
         * typeTwoInfo : [{"one_num":"1","type_one_name":"文体艺术","type_two_id":1,"type_two_name":"书画"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":2,"type_two_name":"音乐"},{"one_num":"1","typeThreeInfo":[{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":1,"type_three_name":"足球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":2,"type_three_name":"篮球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":4,"type_three_name":"乒乓球"}],"type_one_name":"文体艺术","type_two_id":3,"type_two_name":"球类"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":11,"type_two_name":"红烧肉"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":12,"type_two_name":"小炖肉"},{"one_num":"1","type_one_name":"文体艺术","type_two_id":13,"type_two_name":"酱肘子"}]
         * type_one_id : 1
         * type_one_name : 文体艺术
         */

        private String only_num;
        private String type_one_id;
        private String type_one_name;
        private List<TypeTwoInfoBean> typeTwoInfo;

        public String getOnly_num() {
            return only_num;
        }

        public void setOnly_num(String only_num) {
            this.only_num = only_num;
        }

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

        public static class TypeTwoInfoBean implements Serializable{
            /**
             * one_num : 1
             * type_one_name : 文体艺术
             * type_two_id : 1
             * type_two_name : 书画
             * typeThreeInfo : [{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":1,"type_three_name":"足球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":2,"type_three_name":"篮球"},{"one_name":"文体艺术","one_num":1,"two_name":"球类","two_num":3,"type_three_id":4,"type_three_name":"乒乓球"}]
             */

            private String one_num;
            private String type_one_name;
            private int type_two_id;
            private String type_two_name;
            private List<TypeThreeInfoBean> typeThreeInfo;

            public String getOne_num() {
                return one_num;
            }

            public void setOne_num(String one_num) {
                this.one_num = one_num;
            }

            public String getType_one_name() {
                return type_one_name;
            }

            public void setType_one_name(String type_one_name) {
                this.type_one_name = type_one_name;
            }

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

            public List<TypeThreeInfoBean> getTypeThreeInfo() {
                return typeThreeInfo;
            }

            public void setTypeThreeInfo(List<TypeThreeInfoBean> typeThreeInfo) {
                this.typeThreeInfo = typeThreeInfo;
            }

            public static class TypeThreeInfoBean implements Serializable{
                /**
                 * one_name : 文体艺术
                 * one_num : 1
                 * two_name : 球类
                 * two_num : 3
                 * type_three_id : 1
                 * type_three_name : 足球
                 */

                private String one_name;
                private int one_num;
                private String two_name;
                private int two_num;
                private int type_three_id;
                private String type_three_name;

                public String getOne_name() {
                    return one_name;
                }

                public void setOne_name(String one_name) {
                    this.one_name = one_name;
                }

                public int getOne_num() {
                    return one_num;
                }

                public void setOne_num(int one_num) {
                    this.one_num = one_num;
                }

                public String getTwo_name() {
                    return two_name;
                }

                public void setTwo_name(String two_name) {
                    this.two_name = two_name;
                }

                public int getTwo_num() {
                    return two_num;
                }

                public void setTwo_num(int two_num) {
                    this.two_num = two_num;
                }

                public int getType_three_id() {
                    return type_three_id;
                }

                public void setType_three_id(int type_three_id) {
                    this.type_three_id = type_three_id;
                }

                public String getType_three_name() {
                    return type_three_name;
                }

                public void setType_three_name(String type_three_name) {
                    this.type_three_name = type_three_name;
                }
            }
        }
    }
}
