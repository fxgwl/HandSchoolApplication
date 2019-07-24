package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by zhw on 2018/8/24.
 */

public class ReplyInfoThree {


    /**
     * code : 0
     * count : 2
     * data : [{"create_time":"2018-08-24 16:27:40","critic_id":11,"interact_id":"1533269755094","order_id":"1533267395858","records":"光棍节快乐","replier_id":"bfe4c2e2a68e44c08b507a19544f7e4a","sender_id":"d9759e30456a4922bd4fde3952148903","userInfo":{"head_photo":"head/initial.jpg","member_name":"用户_1533276503"}},{"create_time":"2018-08-24 16:20:52","critic_id":10,"interact_id":"1533269755094","order_id":"1533267395858","records":"够够","replier_id":"3008b7e173c24a788ac835f6b4c7e1cc","sender_id":"d9759e30456a4922bd4fde3952148903","userInfo":{"head_photo":"head/initial.jpg","member_name":"用户_1533276503"}}]
     * msg : success
     */

    private int code;
    private int count;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
         * create_time : 2018-08-24 16:27:40
         * critic_id : 11
         * interact_id : 1533269755094
         * order_id : 1533267395858
         * records : 光棍节快乐
         * replier_id : bfe4c2e2a68e44c08b507a19544f7e4a
         * sender_id : d9759e30456a4922bd4fde3952148903
         * userInfo : {"head_photo":"head/initial.jpg","member_name":"用户_1533276503"}
         */

        private String create_time;
        private int critic_id;
        private String interact_id;
        private String head_photo;
        private String order_id;
        private String records;
        private String replier_id;
        private String sender_id;
        private String critic_type;
        private String user_name;
        private UserInfoBean userInfo;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getCritic_id() {
            return critic_id;
        }

        public void setCritic_id(int critic_id) {
            this.critic_id = critic_id;
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

        public String getRecords() {
            return records;
        }

        public void setRecords(String records) {
            this.records = records;
        }

        public String getReplier_id() {
            return replier_id;
        }

        public void setReplier_id(String replier_id) {
            this.replier_id = replier_id;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getCritic_type() {
            return critic_type;
        }

        public void setCritic_type(String critic_type) {
            this.critic_type = critic_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * head_photo : head/initial.jpg
             * member_name : 用户_1533276503
             */

            private String head_photo;
            private String member_name;
            private String user_name;

            public String getHead_photo() {
                return head_photo;
            }

            public void setHead_photo(String head_photo) {
                this.head_photo = head_photo;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }
}
