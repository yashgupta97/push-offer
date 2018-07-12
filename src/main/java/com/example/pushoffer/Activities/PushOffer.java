package com.example.pushoffer.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pushoffer.CustomAlertViews.PushLayout;
import com.example.pushoffer.DB.DbHelper;
import com.example.pushoffer.DB.OfferTable;
import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.R;

import java.io.InputStream;
import java.util.ArrayList;

public class PushOffer {

    private static SQLiteDatabase database;
    private static PushOffer poInstance = null;
    private static ArrayList<Offer> poList;
    private static Offer mOffer = null;


    /* get a Singleton instance of PushOffer class */
    public static PushOffer getPoInstance() {
        if (poInstance == null) {
            poInstance = new PushOffer();
        }
        return poInstance;
    }

    /* Initialize the Database and fetch all Pushes for current session */
    public static void init(Context ctx) {
        database = (new DbHelper(ctx)).getWritableDatabase();
        getSessionOffers();
    }

    /* Add Push to database */
    public static void addOffer(Offer offer) {

        if (OfferTable.checkExist(database, offer.getMsgID())) {
            OfferTable.updateTable(database, offer);
        } else {
            OfferTable.addNewOffer(database, offer);
        }
    }

    /* delete all table entries */
    public static void deleteAllOffers() {
        OfferTable.deleteTable(database);
    }

    /* Returns number of entries in push database. */
    public static long getCount() {
        return OfferTable.getProfilesCount(database);
    }

    private static void showDialog(Context ctx, final Offer offer) {

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.layout_dialog);
        ((TextView) dialog.findViewById(R.id.dialog_msg_title)).setText(offer.getTitle());
        ((TextView) dialog.findViewById(R.id.dialog_msg_body)).setText(offer.getBody());
        setImage((ImageView) dialog.findViewById(R.id.dialog_msg_img),offer.getUrl());
        ((TextView) dialog.findViewById(R.id.dialog_msg_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /* return Arraylist with all pushes in database */
    public static ArrayList<Offer> getAllOffers(String category) {
        return OfferTable.getAllOffers(database,category);
    }

    /* delete particular push from database */
    public static void deleteOffer(String id) {
        OfferTable.deleteOffer(database, id);
    }

    /*
     * Set cancelled flag of all Pushes to false.
     * Update session offers list
     */
    public static void changeAllCancelFlag() {
        OfferTable.updateAllCancelFlag(database);
        getSessionOffers();
    }

    /*
     * Set Open flag of all Pushes to false.
     * Update session offers list
     */
    public static void changeAllOpenFlag() {
        OfferTable.updateAllOpenFlag(database);
    }

    /* get all pushes for current session */
    private static void getSessionOffers() {
        poList = OfferTable.getSessionOffers(database);
    }

    /* Display all available Pushes from the Database */
    public static void showAllOffers(Activity activity) {
        if (activity == null)
            return;
        Intent intent = new Intent(activity, DisplayOfferActivity.class);
        activity.startActivity(intent);
    }

    /*
     * Set layout according to availability of Push.
     * Dialog or banner style Push displayed based on its type.
     */
    public static void setView(Activity activity, PushLayout banner) {
        String act = activity.getClass().getSimpleName();
        for (Offer offer : poList) {
            if (offer.getScreen().equals(act)) {
                mOffer = offer;
                break;
            }
        }
        if (mOffer == null || mOffer.isCancelled()) {
            mOffer = OfferTable.getOffer(database, act);
            if (mOffer == null) {
                return;
            }
        }

//        String date= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
//        Log.d("pushoffer", "getOffer: "+date);
//        if (mOffer.getValidity().equals(date)) {
//            deleteOffer(mOffer.getMsgID());
//            getOffer(activity, myCustomLayout);
//        }

        if (mOffer.isOneTime()) {
            showDialog(activity.getWindow().getContext(), mOffer);
            mOffer.setCancelled(true);
            mOffer.setOpen(true);
            OfferTable.updateOpenFlag(database, mOffer.getMsgID(), true);
            OfferTable.updateCancelledFlag(database, mOffer.getMsgID(), true);
        } else {
            banner.createView(mOffer);
            banner.setVisibility(View.VISIBLE);
            mOffer.setOpen(true);
            OfferTable.updateOpenFlag(database, mOffer.getMsgID(), true);
        }
    }

    /* checks display position of push on screen */
    public static boolean isTop() {
        return mOffer.getPosition().toLowerCase().equals("top");
    }

    /* set cancel flag true for given push */
    public static void changeCancelledFlag(Activity activity) {
        String act = activity.getClass().getSimpleName();
        mOffer.setCancelled(true);
        OfferTable.updateCancelledFlag(database, mOffer.getMsgID(), true);
    }

    /* Download image from url task */
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result==null){
                bmImage.setImageResource(R.mipmap.ic_launcher_round);
            }
            bmImage.setImageBitmap(result);
        }
    }

    /* set image in imageview */
    public static void setImage(ImageView view,String url) {
        new DownloadImageTask(view).execute(url);
    }
}