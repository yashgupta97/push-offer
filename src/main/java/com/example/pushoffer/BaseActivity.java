package com.example.pushoffer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.example.pushoffer.CustomAlertViews.PushLayout;

public class BaseActivity extends AppCompatActivity {

    RelativeLayout frame;
    public ViewGroup container;
    private PushLayout banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        frame = findViewById(R.id.act_layout);
        container = findViewById(R.id.act_container);
        banner = findViewById(R.id.act_banner);

    }

    public void bannerPos(boolean isTop) {
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


    public void setView(final Activity act, final View layout) {
        container.addView(layout);
        PushOffer.setView(act, banner);

        try {
            bannerPos(PushOffer.isTop());
            banner.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation transAnim;

                    if (PushOffer.isTop()) {
                        transAnim = slideUp(banner.getHeight());
                    } else {
                        transAnim = slideDown(banner.getHeight());
                    }

                    transAnim.setDuration(1000);
                    transAnim.setFillBefore(true);
                    transAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            banner.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    frame.startAnimation(transAnim);
                    PushOffer.changeCancelledFlag(act);
                }
            });


        } catch (Exception e) {

        }
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
