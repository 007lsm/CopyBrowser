package com.example.admin.myapplication.databean;

public class MsgInfo {
    private long msgId;//消息Id
    private String ownerId;//消息属于哪个用户
    private String relatedId;//消息关联到哪个用户;
    private String body;//消息体
    private long time;//消息发送接收时间
    private int direct;// 消息的方向
    private int status;//消息的状态
    private int type;//消息的类型

    public long getMsgId() {
        return msgId;
    }

    public MsgInfo setMsgId(long msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public MsgInfo setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getBody() {
        return body;
    }

    public MsgInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public long getTime() {
        return time;
    }

    public MsgInfo setTime(long time) {
        this.time = time;
        return this;
    }

    public int getDirect() {
        return direct;
    }

    public MsgInfo setDirect(int direct) {
        this.direct = direct;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MsgInfo setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
