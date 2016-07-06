package com.whunf.putaomovieday1.common.animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.whunf.putaomovieday1.R;


/**
 * 
 ************************************************ <br>
 * 文件名称: AnimationManager.java <br>
 * 版权声明: <b>深圳市葡萄信息技术有限公司</b> 版权所有 <br>
 * 创建人员: putao_ffh <br>
 * 文件描述: <br>
 * 修改时间: 2015-12-15 下午3:30:42 <br>
 * 修改历史: 2015-12-15 1.00 初始版本 <br>
 ************************************************* 
 */
public class AnimationManager
{

    private ImageView mImageView;

    private int mPosition = 0;

    private float mStart;

    private float mEnd;

    private int mDegreeCount;

    private int[] mImageResIds = new int[]
    { R.drawable.putao_icon_loading_01, R.drawable.putao_icon_loading_02, R.drawable.putao_icon_loading_03,
            R.drawable.putao_icon_loading_04, R.drawable.putao_icon_loading_05, R.drawable.putao_icon_loading_06,
            R.drawable.putao_icon_loading_07 };

    public AnimationManager(ImageView imageView)
    {
        mImageView = imageView;
    }

    public void applyRotation(int degreeCount, float start, float end)
    {
        mDegreeCount = degreeCount;
        mStart = start;
        mEnd = end;
        mImageView.post(new SwapViews());
    }

    public void destroyAnimation()
    {
        if (mImageView != null)
        {
            mImageView.clearAnimation();
            mImageView.removeCallbacks(null);
        }
    }
    
    private final class SwapViews implements Runnable, AnimationListener
    {
        public void run()
        {
            final float centerX = mImageView.getWidth() / 2.0f;
            final float centerY = mImageView.getHeight() / 2.0f;
            float fromDegrees = mDegreeCount * 90 + mStart;
            float toDegrees = mDegreeCount * 90 + mEnd;
            if (mDegreeCount % 2 != 0)
            {
                mPosition++;
            }
            if (mPosition < mImageResIds.length && mPosition >= 0)
            {
                mImageView.setImageResource(mImageResIds[mPosition]);
            }
            else
            {
                mPosition = 0;
                mImageView.setImageResource(mImageResIds[mPosition]);
            }
            Rotate3dAnimation rotation;
            rotation = new Rotate3dAnimation(fromDegrees, toDegrees, centerX, centerY, 0.0f, false);
            rotation.setDuration(400);
            rotation.setFillAfter(true);
            if (mDegreeCount % 2 == 0)
            {
                rotation.setInterpolator(new AccelerateInterpolator());
            }
            else
            {
                rotation.setInterpolator(new DecelerateInterpolator());
            }
            rotation.setAnimationListener(this);
            mImageView.startAnimation(rotation);
        }

        @Override
        public void onAnimationEnd(Animation arg0)
        {
            mDegreeCount++;
            mImageView.post(this);
        }

        @Override
        public void onAnimationRepeat(Animation arg0)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation arg0)
        {
            // TODO Auto-generated method stub

        }
    }
}
