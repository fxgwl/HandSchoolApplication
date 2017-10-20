package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class PJDetailBean {

    /**
     * data : {"anonymous":"a","class_score":"4","contents":"haohkjshfkdjsv","contents_num":7,"course_id":"1505888150982","course_money":"333","course_name":"舞蹈","course_num":"2","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","interact_id":"1506399594956","interact_time":"2017-09-26 12:19:54","order_id":"1505979101519","replyInfo":[{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"edqwrfqwfqw很好，很好，好很好，很好，好很好，很好，好很好，很好，好","reply_id":"1506408633520","reply_state":"0","reply_time":"2017-09-26 14:50:33","reply_type":"1","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"很好，ass大西安的很好，ass大西安的很好，ass大西安的很好，ass大西安的很好，ass大西安的","reply_id":"1506409282581","reply_state":"0","reply_time":"2017-09-26 15:01:22","reply_type":"0","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"哈哈哈我是学堂回复","reply_id":"1508469224854","reply_state":"0","reply_time":"2017-10-20 11:13:44","reply_type":"1","send_name":"aasad德","send_photo":"head/naruto@2x.png","send_uid":"4c975ad7eeb646f7b0e06cabc8290ddd"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"我来了","reply_id":"1508469418446","reply_state":"0","reply_time":"2017-10-20 11:16:58","reply_type":"2","send_name":"菲菲","send_photo":"images/head.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"经历过","reply_id":"1508488578657","reply_state":"0","reply_time":"2017-10-20 16:36:18","reply_type":"1","send_name":"aasad德","send_photo":"head/naruto@2x.png","send_uid":"4c975ad7eeb646f7b0e06cabc8290ddd"}],"school_score":"4","send_name":"aasad德","send_photo":"head/naruto@2x.png","send_uid":"4c975ad7eeb646f7b0e06cabc8290ddd","user_id":"9b57d807ade3407b991631b2eaebdb33"}
     * msg : 成功
     * result : 0
     */

    private DataBean data;
    private String msg;
    private int result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * anonymous : a
         * class_score : 4
         * contents : haohkjshfkdjsv
         * contents_num : 7
         * course_id : 1505888150982
         * course_money : 333
         * course_name : 舞蹈
         * course_num : 2
         * course_photo : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
         * interact_id : 1506399594956
         * interact_time : 2017-09-26 12:19:54
         * order_id : 1505979101519
         * replyInfo : [{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"edqwrfqwfqw很好，很好，好很好，很好，好很好，很好，好很好，很好，好","reply_id":"1506408633520","reply_state":"0","reply_time":"2017-09-26 14:50:33","reply_type":"1","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"很好，ass大西安的很好，ass大西安的很好，ass大西安的很好，ass大西安的很好，ass大西安的","reply_id":"1506409282581","reply_state":"0","reply_time":"2017-09-26 15:01:22","reply_type":"0","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"哈哈哈我是学堂回复","reply_id":"1508469224854","reply_state":"0","reply_time":"2017-10-20 11:13:44","reply_type":"1","send_name":"aasad德","send_photo":"head/naruto@2x.png","send_uid":"4c975ad7eeb646f7b0e06cabc8290ddd"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"我来了","reply_id":"1508469418446","reply_state":"0","reply_time":"2017-10-20 11:16:58","reply_type":"2","send_name":"菲菲","send_photo":"images/head.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828"},{"course_id":"1505888150982","from_name":"asd阿萨德","from_photo":"images/head.jpg","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1506399594956","order_id":"1505979101519","reply_content":"经历过","reply_id":"1508488578657","reply_state":"0","reply_time":"2017-10-20 16:36:18","reply_type":"1","send_name":"aasad德","send_photo":"head/naruto@2x.png","send_uid":"4c975ad7eeb646f7b0e06cabc8290ddd"}]
         * school_score : 4
         * send_name : aasad德
         * send_photo : head/naruto@2x.png
         * send_uid : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_id : 9b57d807ade3407b991631b2eaebdb33
         */

        private String anonymous;
        private String class_score;
        private String contents;
        private int contents_num;
        private String course_id;
        private String course_money;
        private String course_name;
        private String course_num;
        private String course_photo;
        private String interact_id;
        private String interact_time;
        private String order_id;
        private String school_score;
        private String send_name;
        private String send_photo;
        private String send_uid;
        private String user_id;
        private List<ReplyInfoBean> replyInfo;

        public String getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(String anonymous) {
            this.anonymous = anonymous;
        }

        public String getClass_score() {
            return class_score;
        }

        public void setClass_score(String class_score) {
            this.class_score = class_score;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getContents_num() {
            return contents_num;
        }

        public void setContents_num(int contents_num) {
            this.contents_num = contents_num;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_money() {
            return course_money;
        }

        public void setCourse_money(String course_money) {
            this.course_money = course_money;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getCourse_num() {
            return course_num;
        }

        public void setCourse_num(String course_num) {
            this.course_num = course_num;
        }

        public String getCourse_photo() {
            return course_photo;
        }

        public void setCourse_photo(String course_photo) {
            this.course_photo = course_photo;
        }

        public String getInteract_id() {
            return interact_id;
        }

        public void setInteract_id(String interact_id) {
            this.interact_id = interact_id;
        }

        public String getInteract_time() {
            return interact_time;
        }

        public void setInteract_time(String interact_time) {
            this.interact_time = interact_time;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getSchool_score() {
            return school_score;
        }

        public void setSchool_score(String school_score) {
            this.school_score = school_score;
        }

        public String getSend_name() {
            return send_name;
        }

        public void setSend_name(String send_name) {
            this.send_name = send_name;
        }

        public String getSend_photo() {
            return send_photo;
        }

        public void setSend_photo(String send_photo) {
            this.send_photo = send_photo;
        }

        public String getSend_uid() {
            return send_uid;
        }

        public void setSend_uid(String send_uid) {
            this.send_uid = send_uid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<ReplyInfoBean> getReplyInfo() {
            return replyInfo;
        }

        public void setReplyInfo(List<ReplyInfoBean> replyInfo) {
            this.replyInfo = replyInfo;
        }

        public static class ReplyInfoBean {
            /**
             * course_id : 1505888150982
             * from_name : a***德
             * from_photo : head/naruto@2x.png
             * from_uid : 9b57d807ade3407b991631b2eaebdb33
             * interact_id : 1506399594956
             * order_id : 1505979101519
             * reply_content : edqwrfqwfqw很好，很好，好很好，很好，好很好，很好，好很好，很好，好
             * reply_id : 1506408633520
             * reply_state : 0
             * reply_time : 2017-09-26 14:50:33
             * reply_type : 1
             * send_name : 自行车
             * send_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg
             * send_uid : 8841f54f7b574f06a470ee9002043f8d
             */

            private String course_id;
            private String from_name;
            private String from_photo;
            private String from_uid;
            private String interact_id;
            private String order_id;
            private String reply_content;
            private String reply_id;
            private String reply_state;
            private String reply_time;
            private String reply_type;
            private String send_name;
            private String send_photo;
            private String send_uid;

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getFrom_name() {
                return from_name;
            }

            public void setFrom_name(String from_name) {
                this.from_name = from_name;
            }

            public String getFrom_photo() {
                return from_photo;
            }

            public void setFrom_photo(String from_photo) {
                this.from_photo = from_photo;
            }

            public String getFrom_uid() {
                return from_uid;
            }

            public void setFrom_uid(String from_uid) {
                this.from_uid = from_uid;
            }

            public String getInteract_id() {
                return interact_id;
            }

            public void setInteract_id(String interact_id) {
                this.interact_id = interact_id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getReply_content() {
                return reply_content;
            }

            public void setReply_content(String reply_content) {
                this.reply_content = reply_content;
            }

            public String getReply_id() {
                return reply_id;
            }

            public void setReply_id(String reply_id) {
                this.reply_id = reply_id;
            }

            public String getReply_state() {
                return reply_state;
            }

            public void setReply_state(String reply_state) {
                this.reply_state = reply_state;
            }

            public String getReply_time() {
                return reply_time;
            }

            public void setReply_time(String reply_time) {
                this.reply_time = reply_time;
            }

            public String getReply_type() {
                return reply_type;
            }

            public void setReply_type(String reply_type) {
                this.reply_type = reply_type;
            }

            public String getSend_name() {
                return send_name;
            }

            public void setSend_name(String send_name) {
                this.send_name = send_name;
            }

            public String getSend_photo() {
                return send_photo;
            }

            public void setSend_photo(String send_photo) {
                this.send_photo = send_photo;
            }

            public String getSend_uid() {
                return send_uid;
            }

            public void setSend_uid(String send_uid) {
                this.send_uid = send_uid;
            }
        }
    }
}
