package com.kang.customviewproject.widght;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kang.customviewproject.R;


/**
 * 1.将布局加载进来
 * 2.绘制三个动画
 * 3.将下落和阴影缩放进行绑定
 * 4.设置监听，当落地时进行形状变化以及开始旋转
 * 5.设置差值器
 */
public class LodingShapLayout extends LinearLayout {

    private ShapSwitch shapLoding;
    private View shadowLoding;
    private boolean isBottom = true;
    private final long DURING_TIME = 500;
    ObjectAnimator shapAnimator;
    ObjectAnimator shadowAnimator;
    ObjectAnimator rotationshapAnimator;
    AnimatorSet animatorSet;
    private boolean isStopAnimation;//隐藏的时候停止动画

    public LodingShapLayout(Context context) {
        this(context, null);
    }

    public LodingShapLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LodingShapLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LodingShapLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initShap();
    }

    /**
     * @return void
     * @description 初始化数据
     * @time 2021/8/20 10:37
     */
    private void initShap() {
        inflate(getContext(), R.layout.loading_shap, this);
        shadowLoding = findViewById(R.id.shadowLoding);
        shapLoding = findViewById(R.id.shapLoding);
        animatorSet = new AnimatorSet();
        startAnimation();
    }

    private void startAnimation() {
        fallAnimation();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isStopAnimation) {
                    return;
                }
                if (isBottom) {
                    shapLoding.exchange();
                    rotateAnimation();
                    isBottom = false;
                    upAnimation();
                } else {
                    isBottom = true;
                    fallAnimation();
                }
            }
        });

    }

    /**
     * @description 下落动画
     * @time 2021/8/20 11:20
     */
    private void fallAnimation() {
        shapAnimator = ObjectAnimator.ofFloat(shapLoding, "translationY", 0, dipToPx(40));
        shadowAnimator = ObjectAnimator.ofFloat(shadowLoding, "scaleX", 0.5f, 1f);
        animatorSet.playTogether(shadowAnimator, shapAnimator);
        animatorSet.setDuration(DURING_TIME);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }

    /**
     * @description 上升动画
     * @time 2021/8/20 11:20
     */
    private void upAnimation() {
        shapAnimator = ObjectAnimator.ofFloat(shapLoding, "translationY", dipToPx(40), 0);
        shadowAnimator = ObjectAnimator.ofFloat(shadowLoding, "scaleX", 1f, 0.5f);
        animatorSet.playTogether(shadowAnimator, shapAnimator);
        animatorSet.setDuration(DURING_TIME);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    /**
     * @description 图形旋转
     * @time 2021/8/20 12:17
     */
    private void rotateAnimation() {
        switch (shapLoding.getmCurrtenShap()) {
            case CIRCLE:
                break;
            case SQUARE:
                rotationshapAnimator = ObjectAnimator.ofFloat(shapLoding, "rotation", 0, 180);
                rotationshapAnimator.setDuration(DURING_TIME * 2);
                rotationshapAnimator.start();
                break;
            case TRIGON:
                rotationshapAnimator = ObjectAnimator.ofFloat(shapLoding, "rotation", 0, 240);
                rotationshapAnimator.setDuration(DURING_TIME * 2);
                rotationshapAnimator.start();
                break;
        }

    }

    private int dipToPx(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorSet.cancel();
    }

    public void setStopAnimation(boolean isStop) {
        isStopAnimation = isStop;
    }
}
