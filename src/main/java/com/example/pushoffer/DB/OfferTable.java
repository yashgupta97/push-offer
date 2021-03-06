package com.example.pushoffer.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.pushoffer.POJO.Offer;

import java.util.ArrayList;

import static com.example.pushoffer.DB.DbStrings.CMD_CREATE_TABLE_INE;
import static com.example.pushoffer.DB.DbStrings.COMMA;
import static com.example.pushoffer.DB.DbStrings.LBR;
import static com.example.pushoffer.DB.DbStrings.RBR;
import static com.example.pushoffer.DB.DbStrings.TERM;
import static com.example.pushoffer.DB.DbStrings.TYPE_INT;
import static com.example.pushoffer.DB.DbStrings.TYPE_TEXT;

/* Database Table to handle all db operations */

public class OfferTable {

    public static final String TABLE_NAME = "offers_table";

    public interface Columns {
        String MSGID = "msgID";
        String VALIDITY = "validity";
        String SCREEN = "screen";
        String POSITION = "position";
        String URL = "url";
        String DEEPLINK = "reference";
        String PRIORITY = "priority";
        String OPEN = "open_flag";
        String CANCEL = "cancel_flag";
        String TYPE = "type_flag";
        String TITLE = "title";
        String BODY = "body";
        String CATEGORY = "category";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME + LBR
                    + Columns.MSGID + TYPE_TEXT + COMMA
                    + Columns.VALIDITY + TYPE_TEXT + COMMA
                    + Columns.SCREEN + TYPE_TEXT + COMMA
                    + Columns.POSITION + TYPE_TEXT + COMMA
                    + Columns.URL + TYPE_TEXT + COMMA
                    + Columns.DEEPLINK + TYPE_TEXT + COMMA
                    + Columns.PRIORITY + TYPE_INT + COMMA
                    + Columns.OPEN + TYPE_INT + COMMA
                    + Columns.CANCEL + TYPE_INT + COMMA
                    + Columns.TYPE + TYPE_INT + COMMA
                    + Columns.TITLE + TYPE_TEXT + COMMA
                    + Columns.BODY + TYPE_TEXT + COMMA
                    + Columns.CATEGORY + TYPE_TEXT
                    + RBR + TERM;

    public static String[] FULL_PROJECTION = {
            Columns.MSGID, Columns.VALIDITY, Columns.SCREEN, Columns.POSITION, Columns.URL, Columns.DEEPLINK, Columns.PRIORITY, Columns.OPEN, Columns.CANCEL, Columns.TYPE, Columns.TITLE, Columns.BODY, Columns.CATEGORY
    };

    public static String UPD_TABLE_1_2 = "";

    /* add push to database */
    public static long addNewOffer(SQLiteDatabase db, Offer offer) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.MSGID, offer.getMsgID());
        cv.put(Columns.VALIDITY, offer.getValidity());
        cv.put(Columns.SCREEN, offer.getScreen());
        cv.put(Columns.POSITION, offer.getPosition());
        cv.put(Columns.URL, offer.getUrl());
        cv.put(Columns.DEEPLINK, offer.getDeeplink());
        cv.put(Columns.PRIORITY, offer.getPriority());
        cv.put(Columns.OPEN, offer.isOpen() ? 1 : 0);
        cv.put(Columns.CANCEL, offer.isCancelled() ? 1 : 0);
        cv.put(Columns.TYPE, offer.isOneTime() ? 1 : 0);
        cv.put(Columns.TITLE, offer.getTitle());
        cv.put(Columns.BODY, offer.getBody());
        cv.put(Columns.CATEGORY, offer.getCategory());

        return db.insert(TABLE_NAME, null, cv);
    }

    /* retrieve push for given activity from database */
    public static Offer getOffer(SQLiteDatabase db, String activity) {
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + Columns.SCREEN + " = ? and " + Columns.CANCEL + " = ? order by " + Columns.PRIORITY + " limit 1",
                new String[]{activity, "0"});
        String msgId = "", validity = "", screen = "", position = "", url = "", deeplink = "";
        int priority = 0;
        boolean open = false, cancel = false, type = false;
        String title = "", body = "", category = "";
        if (c.getCount() <= 0) {
            return null;
        }

        c.moveToFirst();
        msgId = c.getString(0);
        validity = c.getString(1);
        screen = c.getString(2);
        position = c.getString(3);
        url = c.getString(4);
        deeplink = c.getString(5);
        priority = c.getInt(6);
        open = (1 == c.getInt(7));
        cancel = (1 == c.getInt(8));
        type = (1 == c.getInt(9));
        title = c.getString(10);
        body = c.getString(11);
        category = c.getString(12);
        c.close();

        if (msgId.equals("")) {
            return null;
        }
        return new Offer(msgId, validity, screen, position, url, deeplink, priority, open, cancel, type, title, body, category);
    }

    /* delete push with given messageID */
    public static int deleteOffer(SQLiteDatabase db, String msgID) {
        String Selection = Columns.MSGID + " LIKE ?";
        String[] SelectArgs = {msgID};

        return db.delete(TABLE_NAME, Selection, SelectArgs);
    }

    /* get number of items in db */
    public static long getProfilesCount(SQLiteDatabase db) {
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count;
    }

    /* clear db */
    public static void deleteTable(SQLiteDatabase db) {
        db.execSQL("delete from " + TABLE_NAME);
    }

    /* update push if exists */
    public static void updateTable(SQLiteDatabase db, Offer offer) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.MSGID, offer.getMsgID());
        cv.put(Columns.VALIDITY, offer.getValidity());
        cv.put(Columns.SCREEN, offer.getScreen());
        cv.put(Columns.POSITION, offer.getPosition());
        cv.put(Columns.URL, offer.getUrl());
        cv.put(Columns.DEEPLINK, offer.getDeeplink());
        cv.put(Columns.PRIORITY, offer.getPriority());
        cv.put(Columns.OPEN, offer.isOpen() ? 1 : 0);
        cv.put(Columns.CANCEL, offer.isCancelled() ? 1 : 0);
        cv.put(Columns.TITLE, offer.getTitle());
        cv.put(Columns.BODY, offer.getBody());
        cv.put(Columns.CATEGORY, offer.getCategory());
        db.update(TABLE_NAME, cv, Columns.MSGID + " = ?", new String[]{offer.getMsgID()});
    }

    /* check if push exists */
    public static boolean checkExist(SQLiteDatabase db, String id) {
        String selection = Columns.MSGID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /* get all pushes in database */
    public static ArrayList<Offer> getAllOffers(SQLiteDatabase db, String category) {
        ArrayList<Offer> offerArrayList = new ArrayList<>();
        String selection = Columns.CATEGORY + " LIKE ?";
        String[] selectArgs = {category};
        Cursor c = db.query(TABLE_NAME, null, selection, selectArgs, null, null, Columns.PRIORITY);

        while (c.moveToNext()) {
            int colID = c.getColumnIndex(Columns.MSGID);
            int colValidity = c.getColumnIndex(Columns.VALIDITY);
            int colScreen = c.getColumnIndex(Columns.SCREEN);
            int colPosition = c.getColumnIndex(Columns.POSITION);
            int colUrl = c.getColumnIndex(Columns.URL);
            int colDeeplink = c.getColumnIndex(Columns.DEEPLINK);
            int colPriority = c.getColumnIndex(Columns.PRIORITY);
            int colOpen = c.getColumnIndex(Columns.OPEN);
            int colCancel = c.getColumnIndex(Columns.CANCEL);
            int colType = c.getColumnIndex(Columns.TYPE);
            int colTitle = c.getColumnIndex(Columns.TITLE);
            int colBody = c.getColumnIndex(Columns.BODY);
            int colCategory = c.getColumnIndex(Columns.CATEGORY);
            offerArrayList.add(new Offer(c.getString(colID),
                    c.getString(colValidity),
                    c.getString(colScreen),
                    c.getString(colPosition),
                    c.getString(colUrl),
                    c.getString(colDeeplink),
                    c.getInt(colPriority),
                    (1 == c.getInt(colOpen)),
                    (1 == c.getInt(colCancel)),
                    (1 == c.getInt(colType)),
                    c.getString(colTitle),
                    c.getString(colBody),
                    c.getString(colCategory)));
        }
        c.close();
        return offerArrayList;
    }

    /* change open flag of push */
    public static void updateOpenFlag(SQLiteDatabase db, String id, boolean isOpen) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.OPEN, isOpen ? 1 : 0);

        db.update(TABLE_NAME, cv, Columns.MSGID + " = ?", new String[]{id});
    }

    /* change cancel flag of push */
    public static void updateCancelledFlag(SQLiteDatabase db, String id, boolean isCancelled) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.CANCEL, isCancelled ? 1 : 0);

        db.update(TABLE_NAME, cv, Columns.MSGID + " = ?", new String[]{id});
    }

    /* set all cancelled flags to false */
    public static void updateAllCancelFlag(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.CANCEL, 0);

        db.update(TABLE_NAME, cv, Columns.CANCEL + " = ? ", new String[]{"1"});
    }

    /* set all open flags to false */
    public static void updateAllOpenFlag(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.OPEN, 0);

        db.update(TABLE_NAME, cv, Columns.OPEN + " = ? ", new String[]{"1"});
    }

    /* get highest priority offers for all activities */
    public static ArrayList<Offer> getSessionOffers(SQLiteDatabase db) {
        ArrayList<Offer> offerArrayList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + Columns.CANCEL + " = ? group by " + Columns.SCREEN + " order by " + Columns.VALIDITY, new String[]{"0"});

        while (c.moveToNext()) {
            int colID = c.getColumnIndex(Columns.MSGID);
            int colValidity = c.getColumnIndex(Columns.VALIDITY);
            int colScreen = c.getColumnIndex(Columns.SCREEN);
            int colPosition = c.getColumnIndex(Columns.POSITION);
            int colUrl = c.getColumnIndex(Columns.URL);
            int colDeeplink = c.getColumnIndex(Columns.DEEPLINK);
            int colPriority = c.getColumnIndex(Columns.PRIORITY);
            int colOpen = c.getColumnIndex(Columns.OPEN);
            int colCancel = c.getColumnIndex(Columns.CANCEL);
            int colType = c.getColumnIndex(Columns.TYPE);
            int colTitle = c.getColumnIndex(Columns.TITLE);
            int colBody = c.getColumnIndex(Columns.BODY);
            int colCategory = c.getColumnIndex(Columns.CATEGORY);
            offerArrayList.add(new Offer(c.getString(colID),
                    c.getString(colValidity),
                    c.getString(colScreen),
                    c.getString(colPosition),
                    c.getString(colUrl),
                    c.getString(colDeeplink),
                    c.getInt(colPriority),
                    (1 == c.getInt(colOpen)),
                    (1 == c.getInt(colCancel)),
                    (1 == c.getInt(colType)),
                    c.getString(colTitle),
                    c.getString(colBody),
                    c.getString(colCategory)));
        }
        c.close();
        return offerArrayList;
    }

}
