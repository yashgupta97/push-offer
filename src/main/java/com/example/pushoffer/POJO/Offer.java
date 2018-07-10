package com.example.pushoffer.POJO;

public class Offer {

    String msgID, validity, screen, position, url, reference;
    int priority;
    boolean isOpen, isCancelled, oneTime;
    String title,body;

    public Offer(String msgID, String validity, String screen, String position, String url, String reference, int priority, boolean isOpen, boolean isCancelled, boolean oneTime, String title, String body) {
        this.msgID = msgID;
        this.validity = validity;
        this.screen = screen;
        this.position = position;
        this.url = url;
        this.reference = reference;
        this.priority = priority;
        this.isOpen = isOpen;
        this.isCancelled = isCancelled;
        this.oneTime = oneTime;
        this.title = title;
        this.body = body;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}