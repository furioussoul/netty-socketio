package com.ly.chat.engine;

public class ChatObject {

    private String userName;
    private String message;
    private String sessionId;
    private String mid;
    private Msg msg;

    public ChatObject() {
    }

    public ChatObject(String userName, String message) {
        super();
        this.userName = userName;
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Msg {
        private String text;
        private Long date;
        private String name;
        private String headerUrl;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Long getDate() {
            return date;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }
    }

}
