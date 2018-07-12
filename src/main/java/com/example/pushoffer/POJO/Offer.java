package com.example.pushoffer.POJO;

/* POJO to save Push */

import android.os.Parcel;
import android.os.Parcelable;

public class Offer implements Parcelable {

    String msgID, validity, screen, position, url, deeplink;
    int priority;
    boolean isOpen, isCancelled, oneTime;
    String title, body, category;

    public Offer(String msgID, String validity, String screen, String position, String url, String deeplink, int priority, boolean isOpen, boolean isCancelled, boolean oneTime, String title, String body, String category) {
        this.msgID = msgID;
        this.validity = validity;
        this.screen = screen;
        this.position = position;
        this.url = url;
        this.deeplink = deeplink;
        this.priority = priority;
        this.isOpen = isOpen;
        this.isCancelled = isCancelled;
        this.oneTime = oneTime;
        this.title = title;
        this.body = body;
        this.category = category;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getMsgID() {
        return msgID;
    }

    public String getValidity() {
        return validity;
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

    public String getUrl() {
        return url;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public int getPriority() {
        return priority;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msgID);
        parcel.writeString(validity);
        parcel.writeString(screen);
        parcel.writeString(position);
        parcel.writeString(url);
        parcel.writeString(deeplink);
        parcel.writeInt(priority);
        parcel.writeByte((byte) (isOpen ? 1 : 0));
        parcel.writeByte((byte) (isCancelled ? 1 : 0));
        parcel.writeByte((byte) (oneTime ? 1 : 0));
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(category);
    }

    protected Offer(Parcel in) {
        msgID = in.readString();
        validity = in.readString();
        screen = in.readString();
        position = in.readString();
        url = in.readString();
        deeplink = in.readString();
        priority = in.readInt();
        isOpen = in.readByte() != 0;
        isCancelled = in.readByte() != 0;
        oneTime = in.readByte() != 0;
        title = in.readString();
        body = in.readString();
        category = in.readString();
    }
}
