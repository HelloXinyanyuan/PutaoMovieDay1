package com.whunf.putaomovieday1.common.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.animation.Rotate3dAnimation;
import com.whunf.putaomovieday1.common.util.GraphicUtil;

/**
 * Created by Administrator on 2016/7/7.
 *
 *
 */
public class Rotate3DProgressDialog extends ProgressDialog implements Animation.AnimationListener {
    private static final String TAG = "Rotate3DProgressDialog";
    private int[] mImageResIds = new int[]
            {R.drawable.putao_icon_loading_01, R.drawable.putao_icon_loading_02, R.drawable.putao_icon_loading_03,
                    R.drawable.putao_icon_loading_04, R.drawable.putao_icon_loading_05, R.drawable.putao_icon_loading_06,
                    R.drawable.putao_icon_loading_07};
    private ImageView mImvPd;

    private Animation mRotate3DAnim;

    private int imgIndex = 0;

    public Rotate3DProgressDialog(Context context) {
        super(context);
//        progressdialog: requestFeature() must be called before
//        init();
    }

    public Rotate3DProgressDialog(Context context, int theme) {
        super(context, theme);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init() {
        View v = View.inflate(getContext(), R.layout.layout_rotate3d_pd, null);
        mImvPd = (ImageView) v.findViewById(R.id.imv_pd);
        setContentView(v);
        if (mRotate3DAnim == null) {
            mRotate3DAnim = new Rotate3dAnimation(0, 180, GraphicUtil.dip2px(getContext(), 50), GraphicUtil.dip2px(getContext(), 50), 0, false);
            mRotate3DAnim.setDuration(400);
            mRotate3DAnim.setRepeatMode(Animation.RESTART);
            mRotate3DAnim.setRepeatCount(Animation.INFINITE);
            mRotate3DAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            mRotate3DAnim.setAnimationListener(this);
        }
    }

    @Override
    public void show() {
        super.show();
        mImvPd.startAnimation(mRotate3DAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mRotate3DAnim.cancel();
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        imgIndex++;
        imgIndex%=mImageResIds.length;
        mImvPd.setImageResource(mImageResIds[imgIndex]);
    }
}
