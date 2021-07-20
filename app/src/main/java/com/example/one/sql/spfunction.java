package com.example.one.sql;

import cn.bmob.v3.BmobObject;

public class spfunction extends BmobObject {

        private boolean judge;
        private String message;

        public boolean isJudge() {
            return judge;
        }
        public void setJudge(boolean judge) {
            this.judge = judge;
        }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

}
