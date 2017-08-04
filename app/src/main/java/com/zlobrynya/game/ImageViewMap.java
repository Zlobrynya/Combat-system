package com.zlobrynya.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by Nikita on 29.06.2017.
 */

public class ImageViewMap extends SubsamplingScaleImageView {
    private PointF heroPointer;

    public ImageViewMap(Context context, AttributeSet attr) {
        super(context, attr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas){
        if (isImageLoaded()){
            if (heroPointer == null){
                heroPointer = new PointF(650,1800);
                Log.d("heroPointer", heroPointer.x + " " +  heroPointer.y);
            }
            @SuppressLint("DrawAllocation") Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

            canvas.drawCircle(sourceToViewCoord(heroPointer).x,sourceToViewCoord(heroPointer).y, 10, p);
        }
    }
}
