package com.klisly.bookbox.utils;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.R;

public class TopToastHelper {
    private static final int ANIMATION_DURATION = 600;
    public static final long DURATION_LONG = 1600;
    public static final long DURATION_SHORT = 800;
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static void showTip(TextView textView, String context, long duration) {
        textView.setText(context);
        animateViewIn(textView, duration);
    }

    private static void animateViewIn(View mView, long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.setTranslationY(mView, -mView.getHeight());
            ViewCompat.setAlpha(mView, 0);
            ViewCompat.animate(mView)
                    .translationY(0f)
                    .alpha(1)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            view.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            BookBoxApplication.getInstance().getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateViewOut(mView);
                                }
                            }, duration);
                        }
                    }).start();
        } else {
            Animation anim = android.view.animation.AnimationUtils.loadAnimation(mView.getContext(),
                    R.anim.top_in);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }

    private static void animateViewOut(View mView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(mView)
                    .alpha(0)
                    .translationY(-mView.getHeight())
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
//                            mView.animateChildrenOut(0, ANIMATION_FADE_DURATION);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
//                            onViewHidden(event);
                        }
                    }).start();
        } else {
            Animation anim = android.view.animation.AnimationUtils.loadAnimation(mView.getContext(), R.anim.top_out);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }
}
