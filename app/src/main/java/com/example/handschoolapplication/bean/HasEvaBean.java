package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HasEvaBean {

    /**
     * data : [{"anonymous":"1","class_score":"4.0","contents":"哈哈哈，很满意。。。1","contents_num":1,"course_id":"1506153404911","course_money":"","course_name":"音乐","course_num":"1","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","interact_id":"1508207380079","interact_time":"2017-10-17 10:29:40","order_id":"1507541019445","replyInfo":[{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1508207380079","order_id":"1505979104723","reply_content":"很好，很好，000","reply_id":"1506423578073","reply_state":"0","reply_time":"2017-09-26 18:59:38","reply_type":"1","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1508207380079","order_id":"1505979104723","reply_content":"很好，很好，好很好，很好，好很好，很好，好很好，很好，好很好，111","reply_id":"1506423999706","reply_state":"0","reply_time":"2017-09-26 19:06:39","reply_type":"2","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"}],"school_score":"4.2","send_name":"菲***菲","send_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1506562563625.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"anonymous":"1","class_score":"4.0","contents":"哈哈哈，很满意。。。3","contents_num":1,"course_id":"1506153404913","course_money":"","course_name":"音乐","course_num":"1","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","interact_id":"15082073800791","interact_time":"2017-10-17 10:29:40","order_id":"1507541019443","replyInfo":[{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"15082073800791","order_id":"1505979104723","reply_content":"很好，很好，好很好，很好，好很好，很好，好很好，很好，好很好，444","reply_id":"1506423999709","reply_state":"0","reply_time":"2017-09-26 19:06:39","reply_type":"0","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"}],"school_score":"4.2","send_name":"菲***菲","send_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1506562563625.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"class_score":"5","contents":"很好很好","contents_num":1,"course_id":"1506153404911","course_money":"","course_name":"音乐","course_num":"2","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","interact_id":"1508401051880","interact_time":"2017-10-19 16:17:31","order_id":"1507541019423","school_score":"5","send_name":"菲菲","send_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1508394979925.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"class_score":"4.1","contents":"哈哈哈","contents_num":1,"course_id":"1506153404911","course_money":"","course_name":"音乐","course_num":"1","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","interact_id":"1508401071193","interact_time":"2017-10-19 16:17:51","order_id":"1507541019424","school_score":"3.6","send_name":"菲菲","send_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1508394979925.jpg","send_uid":"772d42a5a0014faa8706f7cd3f6e8828","user_id":"772d42a5a0014faa8706f7cd3f6e8828"}]
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
         * anonymous : 1
         * class_score : 4.0
         * contents : 哈哈哈，很满意。。。1
         * contents_num : 1
         * course_id : 1506153404911
         * course_money :
         * course_name : 音乐
         * course_num : 1
         * course_photo : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
         * interact_id : 1508207380079
         * interact_time : 2017-10-17 10:29:40
         * order_id : 1507541019445
         * replyInfo : [{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1508207380079","order_id":"1505979104723","reply_content":"很好，很好，000","reply_id":"1506423578073","reply_state":"0","reply_time":"2017-09-26 18:59:38","reply_type":"1","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"},{"course_id":"1505888150982","from_name":"a***德","from_photo":"head/naruto@2x.png","from_uid":"9b57d807ade3407b991631b2eaebdb33","interact_id":"1508207380079","order_id":"1505979104723","reply_content":"很好，很好，好很好，很好，好很好，很好，好很好，很好，好很好，111","reply_id":"1506423999706","reply_state":"0","reply_time":"2017-09-26 19:06:39","reply_type":"2","send_name":"自行车","send_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_uid":"8841f54f7b574f06a470ee9002043f8d"}]
         * school_score : 4.2
         * send_name : 菲***菲
         * send_photo : bicths/772d42a5a0014faa8706f7cd3f6e8828/1506562563625.jpg
         * send_uid : 772d42a5a0014faa8706f7cd3f6e8828
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
         */

        private String anonymous;
        private int browse_num;
        private String class_score;
        private String contents;
        private int contents_num;
        private String course_id;
        private String course_money;
        private String course_name;
        private String course_num;
        private String course_photo;
        private String picture_one;
        private String picture_two;
        private String picture_three;
        private String picture_four;
        private String picture_five;
        private String interact_id;
        private String interact_time;
        private String order_id;
        private String school_score;
        private String send_name;
        private String send_photo;
        private String send_uid;
        private String user_id;
        private List<ReplyInfoBean> replyInfo;
        private int unread;

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

        public String getPicture_one() {
            return picture_one;
        }

        public void setPicture_one(String picture_one) {
            this.picture_one = picture_one;
        }

        public String getPicture_two() {
            return picture_two;
        }

        public void setPicture_two(String picture_two) {
            this.picture_two = picture_two;
        }

        public String getPicture_three() {
            return picture_three;
        }

        public void setPicture_three(String picture_three) {
            this.picture_three = picture_three;
        }

        public String getPicture_four() {
            return picture_four;
        }

        public void setPicture_four(String picture_four) {
            this.picture_four = picture_four;
        }

        public String getPicture_five() {
            return picture_five;
        }

        public void setPicture_five(String picture_five) {
            this.picture_five = picture_five;
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

        public int getBrowse_num() {
            return browse_num;
        }

        public void setBrowse_num(int browse_num) {
            this.browse_num = browse_num;
        }

        public List<ReplyInfoBean> getReplyInfo() {
            return replyInfo;
        }

        public void setReplyInfo(List<ReplyInfoBean> replyInfo) {
            this.replyInfo = replyInfo;
        }

        public int getUnread() {
            return unread;
        }

        public void setUnread(int unread) {
            this.unread = unread;
        }

        public static class ReplyInfoBean {
            /**
             * course_id : 1505888150982
             * from_name : a***德
             * from_photo : head/naruto@2x.png
             * from_uid : 9b57d807ade3407b991631b2eaebdb33
             * interact_id : 1508207380079
             * order_id : 1505979104723
             * reply_content : 很好，很好，000
             * reply_id : 1506423578073
             * reply_state : 0
             * reply_time : 2017-09-26 18:59:38
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
