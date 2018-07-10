package com.example.pushoffer;

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

import java.io.InputStream;
import java.util.ArrayList;

public class PushOffer {

    private static SQLiteDatabase database;
    private static PushOffer poInstance = null;
    private static ArrayList<Offer> poList;
    private static Offer mOffer = null;

    public static PushOffer getPoInstance() {
        if (poInstance == null) {
            poInstance = new PushOffer();
        }
        return poInstance;
    }

    public static void init(Context ctx) {
        database = (new DbHelper(ctx)).getWritableDatabase();
        getSessionOffers();
    }

    public static void addOffer(Offer offer) {

        if (OfferTable.checkExist(database, offer.getMsgID())) {
            OfferTable.updateTable(database, offer);
        } else {
            OfferTable.addNewOffer(database, offer);
        }
    }

    public static void deleteAllOffers() {
        OfferTable.deleteTable(database);
    }

    public static long getCount() {
        return OfferTable.getProfilesCount(database);
    }

    private static void showDialog(Context ctx, final Offer offer) {

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.layout_dialog);
        ((TextView) dialog.findViewById(R.id.dialog_msg_title)).setText(offer.getTitle());
        ((TextView) dialog.findViewById(R.id.dialog_msg_body)).setText(offer.getBody());
//        ((TextView) dialog.findViewById(R.id.tv_validity)).setText(offer.getValidity());
//        ((TextView) dialog.findViewById(R.id.tv_priority)).setText(offer.getPriority() + "");

        ((ImageView) dialog.findViewById(R.id.dialog_msg_img)).setImageResource(R.mipmap.ic_launcher_round);
        ((TextView) dialog.findViewById(R.id.dialog_msg_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public static ArrayList<Offer> getAllOffers() {
        return OfferTable.getAllOffers(database);
    }

    public static void deleteOffer(String id) {
        OfferTable.deleteOffer(database, id);
    }

    public static void changeCancelFlag(String id) {
        mOffer.setCancelled(true);
        OfferTable.updateCancelledFlag(database, id, true);
    }

    public static void changeCFlag() {
        OfferTable.updateCFlag(database);
        getSessionOffers();
    }

    public static void changeOFlag() {
        OfferTable.updateOFlag(database);
    }

    /* --------------------------------------------------------------------------------- */


    public static void getSessionOffers() {
        poList = OfferTable.getSessionOffers(database);
    }

    public static void getView(Activity activity, PushLayout plTop, PushLayout plBottom) {
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
            if (mOffer.getPosition().toLowerCase().equals("top")) {
                Log.d("TAG", "showOffer: TOP");
                plTop.createView(plTop, mOffer);
                plTop.setVisibility(View.VISIBLE);
            } else {
                Log.d("TAG", "showOffer: BOTTOM");
                plBottom.createView(plBottom, mOffer);
                plBottom.setVisibility(View.VISIBLE);
            }
            mOffer.setOpen(true);
            OfferTable.updateOpenFlag(database, mOffer.getMsgID(), true);
        }
    }

    public static void showAllOffers(Activity activity) {
        if (activity == null)
            return;
        Intent intent = new Intent(activity, DisplayActivity.class);
        activity.startActivity(intent);
    }

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
        if (mOffer.isOneTime()) {
            showDialog(activity.getWindow().getContext(), mOffer);
            mOffer.setCancelled(true);
            mOffer.setOpen(true);
            OfferTable.updateOpenFlag(database, mOffer.getMsgID(), true);
            OfferTable.updateCancelledFlag(database, mOffer.getMsgID(), true);
        } else {
            banner.createView(banner, mOffer);
            banner.setVisibility(View.VISIBLE);
            mOffer.setOpen(true);
            OfferTable.updateOpenFlag(database, mOffer.getMsgID(), true);
        }
    }

    public static boolean isTop() {
        return mOffer.getPosition().toLowerCase().equals("top");
    }

    public static void changeCancelledFlag(Activity activity) {
        String act = activity.getClass().getSimpleName();
        mOffer.setCancelled(true);
        OfferTable.updateCancelledFlag(database, mOffer.getMsgID(), true);
    }

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
            bmImage.setImageBitmap(result);
        }
    }

    public static void setImage(ImageView view){
        new DownloadImageTask(view).execute(mOffer.getUrl());
    }
}