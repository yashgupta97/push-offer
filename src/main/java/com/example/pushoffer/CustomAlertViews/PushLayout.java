package com.example.pushoffer.CustomAlertViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.PushOffer;
import com.example.pushoffer.R;

public class PushLayout extends FrameLayout {

    View viewTop;

    public TextView title, body;
    public ImageView img, cancel;

    public PushLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        viewTop = inflate(getContext(), R.layout.layout_banner, null);
        addView(viewTop, 0);

        title = viewTop.findViewById(R.id.banner_msg_title);
        body = viewTop.findViewById(R.id.banner_msg_body);
        img = viewTop.findViewById(R.id.banner_msg_img);
        cancel = viewTop.findViewById(R.id.banner_cancel);
    }

    public void createView(final PushLayout layout, final Offer offer) {

        ((TextView) viewTop.findViewById(R.id.banner_msg_title)).setText(offer.getTitle());
        ((TextView) viewTop.findViewById(R.id.banner_msg_body)).setText(offer.getBody());
        ((ImageView) viewTop.findViewById(R.id.banner_msg_img)).setImageResource(R.mipmap.ic_launcher_round);//TODO
//     TODO   PushOffer.setImage( ((ImageView) viewTop.findViewById(R.id.banner_msg_img)));

    }

    private TranslateAnimation slideUp(float height) {
        Log.d("TAG", "slideup: ");
        return new TranslateAnimation(
                0,
                0,
                0,
                -height
        );
    }

    private TranslateAnimation slideDown(float height) {
        Log.d("TAG", "slideDown: ");
        return new TranslateAnimation(
                0,
                0,
                0, height
        );
    }
}
