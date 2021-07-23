package com.example.one.sql;

import cn.bmob.v3.BmobObject;

public class spfunction extends BmobObject {

        private boolean judge;
        private String message;
        private String using_api;
        private String password;

        public boolean isJudge() {
            return judge;
        }
        public void setJudge(boolean judge) {
            this.judge = judge;
        }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getUsing_api() { return using_api; }
        public void setUsing_api(String using_api) { this.using_api = using_api; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

}
