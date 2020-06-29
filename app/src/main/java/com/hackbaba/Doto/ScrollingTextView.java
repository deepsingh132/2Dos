package com.hackbaba.Doto;


import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollingTextView extends TextView implements Runnable {


    private static final float DEFAULT_SPEED = 9.0f;

    public Scroller scroller;
    public float speed = DEFAULT_SPEED;
    public boolean continuousScrolling = true;

    public ScrollingTextView(Context context) {
        super(context);
        scrollerInstance(context);
    }

    public ScrollingTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
        scrollerInstance(context);
    }

    public void scrollerInstance(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (scroller.isFinished() && continuousScrolling) {
            scroll();
        }
        //if (scroller.isFinished() && continuousScrolling) scroll();
    }

    public void scroll() {


        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();
        int totallineHeight = getLineCount() * lineHeight;

        int offset = -1 * visibleHeight;
        int distance = visibleHeight + getLineCount() * lineHeight;
        int duration = (int) (distance * speed);


        scroller.startScroll(0, offset, 0, distance, duration);
        if (continuousScrolling) {
            post(this);
        }


    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    @Override
    public void run() {

        if (scroller.isFinished()) {
            scroll();
        } else {
            post(this);
        }
    }
}



/*

public class ScrollingTextView extends TextView implements Runnable {

    private static final float DEFAULT_SPEED = 15.0f;

    private Scroller scroller;
    private float speed = DEFAULT_SPEED;
    private boolean continuousScrolling = true;

    public ScrollingTextView(Context context) {
        super(context);
        setup(context);
    }

    public ScrollingTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setup(context);
    }

    private void setup(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (scroller.isFinished()) {
            scroll();
        }
    }

    private void scroll() {
        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();

        int offset = -1 * visibleHeight;
        int totallineHeight = getLineCount() * lineHeight;
        int distance = totallineHeight + visibleHeight;
        int duration = (int) (distance * speed);

        if (totallineHeight > visibleHeight) {
            scroller.startScroll(0, offset, 0, distance, duration);

            if (continuousScrolling) {
                post(this);
            }
        }
    }

    @Override
    public void run() {
        if (scroller.isFinished()) {
            scroll();
        } else {
            post(this);
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }
}

 */


/*
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;


  A TextView that scrolls it contents across the screen, in a similar fashion as movie scroll roll
  across the theater screen.

  @author Matthias Kaeppler

public class scroll extends TextView{

    private static final float DEFAULT_SPEED = 15.0f;

    private Scroller scroller;
    private float speed = DEFAULT_SPEED;
    private boolean continuousScrolling = true;











    public scroll(Context context) {
        super(context);
        setup(context);
    }




    public scroll(Context context, AttributeSet attributes) {
        super(context, attributes);
        setup(context);
    }

    private void setup(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (scroller.isFinished()) {
            scroll();
        }
    }




    private void scroll() {
        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();

        int offset = -1 * visibleHeight;
        int totallineHeight = getLineCount() * lineHeight;
        int distance = totallineHeight + visibleHeight;
        int duration = (int) (distance * speed);

        if (totallineHeight > visibleHeight) {
            scroller.startScroll(0, offset, 0, distance, duration);

            if (continuousScrolling) {
                post(this);
            }
        }
    }




    @Override
    public void run() {
        if (scroller.isFinished()) {
            scroll();
        } else {
            post(this);
        }
    }
}
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }
}
*/