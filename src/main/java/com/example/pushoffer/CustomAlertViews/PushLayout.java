package com.example.pushoffer.CustomAlertViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.Activities.PushOffer;
import com.example.pushoffer.R;

/* Custom Layout used to display the Push */

public class PushLayout extends FrameLayout {

    View viewTop;

    public TextView title, body;
    public ImageView img, cancel;

    /* default constructor */
    public PushLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /* initialise view */
    private void initView() {
        viewTop = inflate(getContext(), R.layout.layout_banner, null);
        addView(viewTop, 0);

        title = viewTop.findViewById(R.id.banner_msg_title);
        body = viewTop.findViewById(R.id.banner_msg_body);
        img = viewTop.findViewById(R.id.banner_msg_img);
        cancel = viewTop.findViewById(R.id.banner_cancel);
    }

    /* set view*/
    public void createView(final Offer offer) {
        ((TextView) viewTop.findViewById(R.id.banner_msg_title)).setText(offer.getTitle());
        ((TextView) viewTop.findViewById(R.id.banner_msg_body)).setText(offer.getBody());
        PushOffer.setImage(((ImageView) viewTop.findViewById(R.id.banner_msg_img)),offer.getUrl());
    }
}
