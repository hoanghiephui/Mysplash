package com.wallpaper.unsplash.common.ui.widget.nestedScrollView;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.clockbyte.admobadapter.AdmobRecyclerAdapterWrapper;
import com.google.android.gms.ads.AdRequest;
import com.wallpaper.unsplash.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzahn.runOnUiThread;


/**
 * Nested scroll frame layout.
 *
 * This FrameLayout can dispatch nested scrolling action.
 *
 * */

public abstract class NestedScrollFrameLayout extends FrameLayout
        implements NestedScrollingChild2, NestedScrollingParent2 {

    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    private boolean forceScrolling;

    private boolean isBeingDragged;
    @DirectionRule
    private int swipeDir;
    private float oldY;
    private int lastOffsetY;
    private float touchSlop;

    private static final int DIR_TOP = 1;
    private static final int DIR_BOTTOM = -1;
    private static final int DIR_NULL = 0;
    @IntDef({DIR_TOP, DIR_BOTTOM, DIR_NULL})
    private @interface DirectionRule {}

    public AdmobRecyclerAdapterWrapper adapterWrapper;
    private Timer updateAdsTimer;

    public NestedScrollFrameLayout(Context context) {
        super(context);
        this.initialize();
    }

    public NestedScrollFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public NestedScrollFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initialize();
    }

    private void initialize() {
        this.nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        this.nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        setNestedScrollingEnabled(true);

        setForceScrolling(false);

        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (forceScrolling) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isBeingDragged = false;
                    swipeDir = DIR_NULL;
                    oldY = ev.getY();
                    lastOffsetY = 0;
                    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, 0);
                    return false;

                case MotionEvent.ACTION_MOVE:
                    int deltaY = (int) (oldY - ev.getY() + lastOffsetY);
                    if (!isBeingDragged) {
                        if (Math.abs(deltaY) > touchSlop) {
                            isBeingDragged = true;
                        } else {
                            swipeDir = DIR_NULL;
                            oldY = ev.getY();
                            lastOffsetY = 0;
                        }
                    }
                    return isBeingDragged;
            }
        } else {
            super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (forceScrolling) {
                    return false;
                } else {
                    isBeingDragged = false;
                    swipeDir = DIR_NULL;
                    oldY = ev.getY();
                    lastOffsetY = 0;
                    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, 0);
                    break;
                }

            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (oldY - ev.getY() + lastOffsetY);
                if (!isBeingDragged && !forceScrolling) {
                    if (Math.abs(deltaY) > touchSlop) {
                        isBeingDragged = true;
                    } else {
                        swipeDir = DIR_NULL;
                        oldY = ev.getY();
                        lastOffsetY = 0;
                    }
                }
                if (isBeingDragged) {
                    if (swipeDir == DIR_NULL
                            || swipeDir * deltaY > 0 || (swipeDir * deltaY < 0 && Math.abs(deltaY) > touchSlop)) {
                        int[] total = new int[] {0, deltaY};
                        int[] consumed = new int[] {0, 0};
                        int y = (int) (isParentOffset () ? ((View) getParent()).getY() : getY());
                        if (dispatchNestedPreScroll(total[0], total[1], consumed, null, 0)) {
                            total[0] -= consumed[0];
                            total[1] -= consumed[1];
                        }
                        dispatchNestedScroll(consumed[0], consumed[1], total[0], total[1], null, 0);
                        swipeDir = deltaY == 0 ? DIR_NULL : (deltaY > 0 ? DIR_TOP : DIR_BOTTOM);
                        oldY = ev.getY();
                        lastOffsetY = (int) (y - (isParentOffset () ? ((View) getParent()).getY() : getY()));
                    }
                }
                if (forceScrolling) {
                    return isBeingDragged;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBeingDragged = false;
                stopNestedScroll(0);
                break;
        }
        return true;
    }

    /**
     * If return true, the view will eliminate the error by compute the offset of it's parent view.
     * */
    public abstract boolean isParentOffset();

    public void setForceScrolling(boolean forceScrolling) {
        this.forceScrolling = forceScrolling;
    }

    // interface.

    // nested scrolling parent.

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes, int type) {
        startNestedScroll(nestedScrollAxes, type);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && type == 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes, int type) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        stopNestedScroll(type);
        nestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, new int[] {0, 0}, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, int[] consumed, int type) {
        dispatchNestedPreScroll(dx, dy, consumed, new int[] {0, 0}, type);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }

    // nested scrolling child.

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return nestedScrollingChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        nestedScrollingChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return nestedScrollingChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        return nestedScrollingChildHelper.dispatchNestedScroll(
                dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapterContent() {
        return null;
    }

    ////ADS
    public void initAds() {
        //your test devices' ids
        String[] testDevicesIds = new String[]{AdRequest.DEVICE_ID_EMULATOR};
        //when you'll be ready for release please use another ctor with admobReleaseUnitId instead.
        adapterWrapper = new AdmobRecyclerAdapterWrapper(getContext(), "ca-app-pub-1329831382083956/5509947025");
        //By default both types of ads are loaded by wrapper.
        // To set which of them to show in the list you should use an appropriate ctor
        //adapterWrapper = new AdmobRecyclerAdapterWrapper(this, testDevicesIds, EnumSet.of(EAdType.ADVANCED_INSTALLAPP));

        //wrapping your adapter with a AdmobAdapterWrapper.
        if (getAdapterContent() != null) {
            adapterWrapper.setAdapter(getAdapterContent());
        }
        //inject your custom layout and strategy of binding for installapp/content  ads
        //here you should pass the extended NativeAdLayoutContext
        //by default it has a value InstallAppAdLayoutContext.getDefault()
        //adapterWrapper.setInstallAdsLayoutContext(...);
        //by default it has a value ContentAdLayoutContext.getDefault()
        //adapterWrapper.setContentAdsLayoutContext(...);

        //Sets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
        adapterWrapper.setLimitOfAds(100);

        //Sets the number of your data items between ad blocks, by default it equals to 10.
        //You should set it according to the Admob's policies and rules which says not to
        //display more than one ad block at the visible part of the screen,
        // so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
        adapterWrapper.setNoOfDataBetweenAds(10);
        adapterWrapper.setFirstAdIndex(2);
    }



    /*
    * Could be omitted. It's only for updating an ad blocks in each 60 seconds without refreshing the list
     */
    public void initUpdateAdsTimer() {
        updateAdsTimer = new Timer();
        updateAdsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (adapterWrapper != null) {
                            adapterWrapper.requestUpdateAd();
                        }
                    }
                });
            }
        }, 60*1000, 60*1000);
    }

    public void onDestroy() {
        if(updateAdsTimer!=null)
            updateAdsTimer.cancel();
        if (adapterWrapper != null) {
            adapterWrapper.destroyAds();
        }
    }
}
