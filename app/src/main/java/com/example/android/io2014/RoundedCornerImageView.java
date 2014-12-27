package com.example.android.io2014;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by shed on 12/26/14.
 *
 */
public class RoundedCornerImageView extends ImageView {
    private Bitmap mBitmap;
    private Paint mPaint;


    public RoundedCornerImageView(Context context) {
        super(context);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (null == getDrawable()) {
            return;
        }
        Bitmap tmp = drawableToBitmap(getDrawable());

        BitmapShader shader = new BitmapShader(tmp,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0.0f, 0.0f,w, h);

        mBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        int radius = getResources().getDimensionPixelOffset(R.dimen.card_corner_radius);
        canvas.drawRoundRect(rect, radius, radius, paint);

        tmp.recycle();

//        Drawable d;
//        d.setTint(2);
        int tint = 4;
        mPaint = new Paint();

        ColorFilter filter = new LightingColorFilter(getResources().getColor(R.color.photo_tint),0);
        mPaint.setColorFilter(filter);

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (null != mBitmap) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
        else {
            super.onDraw(canvas);
        }
    }

    @Override
    public Drawable getDrawable() {
        if (null != mBitmap) {
            return new BitmapDrawable(getResources(),mBitmap);
        }
        return super.getDrawable();
    }

    private Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
