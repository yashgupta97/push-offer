package com.example.pushoffer.Activities;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pushoffer.CustomAlertViews.PushLayout;
import com.example.pushoffer.R;

/* Used to wrap Application Activity with Push layout and display push if available */

public class BaseActivity extends AppCompatActivity {

    private RelativeLayout frame;
    private ViewGroup container;
    private PushLayout banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        frame = findViewById(R.id.act_layout);
        container = findViewById(R.id.act_container);
        banner = findViewById(R.id.act_banner);

        LayoutTransition t = new LayoutTransition();
        t.enableTransitionType(LayoutTransition.CHANGING);
        t.setDuration(1000);
        frame.setLayoutTransition(t);

    }

    /* determine layout params for layout views */
    private void bannerPos(boolean isTop) {
        if (isTop) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) banner.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            banner.setLayoutParams(params);

            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) container.getLayoutParams();
            containerParams.addRule(RelativeLayout.BELOW, R.id.act_banner);
            container.setLayoutParams(containerParams);
        } else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) banner.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            banner.setLayoutParams(params);

            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) container.getLayoutParams();
            containerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            containerParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            containerParams.addRule(RelativeLayout.ABOVE, R.id.act_banner);
            container.setLayoutParams(containerParams);
        }
    }

    /* Receive Activity context and layout.
     * create and display new layout */
    public void setView(final Activity act, final View layout) {
        container.addView(layout);
        PushOffer.setView(act, banner);
        int interval = 30000;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                banner.setVisibility(View.GONE);
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
        try {
            bannerPos(PushOffer.isTop());
            banner.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    banner.setVisibility(View.GONE);
//                    TranslateAnimation transAnim;

//                    if (PushOffer.isTop()) {
//                        transAnim = slideUp(banner.getHeight());
//
//                    } else {
//                        transAnim = slideDown(banner.getHeight());
//
//                    }
//
//                    transAnim.setDuration(1000);
//                    transAnim.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            banner.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//
//                    banner.startAnimation(transAnim);
                    PushOffer.changeCancelledFlag(act);
                }
            });

        } catch (Exception e) {

        }
    }
//
//    private TranslateAnimation slideUp(float height) {
//        Log.d("TAG", "slideup: ");
//        return new TranslateAnimation(
//                0,
//                0,
//                0,
//                -height
//        );
//    }
//
//    private TranslateAnimation slideDown(float height) {
//        Log.d("TAG", "slideDown: ");
//        return new TranslateAnimation(
//                0,
//                0,
//                0, height
//        );
//    }
//
}
