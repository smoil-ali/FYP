package com.reactive.fyp.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reactive.fyp.R;

public class MaskViewText extends FrameLayout {
    final String TAG = MaskViewText.class.getSimpleName();
    Paint paint = new Paint();
    public int RIGHT;
    public int BOTTOM;
    public boolean touched = false;
    Bitmap bm;
    private PointF start = new PointF();

    public MaskViewText(@NonNull Context context) {
        super(context);
    }

    public MaskViewText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
    }

    public MaskViewText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaskViewText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"In Draw");
        Log.i(TAG,touched+"");
        if (touched) {
            @SuppressLint("UseCompatLoadingForDrawables") BitmapDrawable bd = (BitmapDrawable) getContext().getDrawable(R.drawable.cancel);
            bm = bd.getBitmap();
            paint.setColor(getContext().getColor(R.color.tooltip_background_dark));
            paint.setStyle(Paint.Style.FILL);
            int canvasW = getWidth();
            int canvasH = getHeight();
            Log.i(TAG, "Width " + canvasW + " Height " + canvasH);
            Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
            Log.i(TAG, "center point " + centerOfCanvas);
            Log.i(TAG, "Pxs " + canvasW + " " + canvasH);
            int left = centerOfCanvas.x - (canvasW / 2);
            int top = centerOfCanvas.y - (canvasH / 2);
            int right = centerOfCanvas.x + (canvasW / 2);
            int bottom = centerOfCanvas.y + (canvasH / 2);
            RIGHT=right;
            BOTTOM=bottom;
            Log.i(TAG, "left " + left + " top " + top + " right " + right + " bottom " + bottom);
            @SuppressLint("DrawAllocation") Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, paint);
            paint.setStrokeWidth(5);
            paint.setColor(getContext().getColor(R.color.tooltip_background_light));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
            canvas.drawBitmap(getResizedBitmap(bm,64,64),left,top,paint);
            bd=(BitmapDrawable)getContext().getDrawable(R.drawable.horizontal_symmetry);
            bm=bd.getBitmap();
            canvas.drawBitmap(getResizedBitmap(bm,42,42),right-55,top+10,paint);
            bd=(BitmapDrawable)getContext().getDrawable(R.drawable.reload);
            bm=bd.getBitmap();
            canvas.drawBitmap(getResizedBitmap(bm,48,48),left+10,bottom-60,paint);
            bd=(BitmapDrawable)getContext().getDrawable(R.drawable.zoom);
            bm=bd.getBitmap();
            canvas.drawBitmap(getResizedBitmap(bm,48,48),right-55,bottom-60,paint);
        }else {
            Paint clearPaint = new Paint();
            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(0, 0, 0, 0, clearPaint);
        }
    }

    public void clear(){
        Log.i(TAG,"here");
        touched=false;
        invalidate();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        if (bm != null && !bm.isRecycled()) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
        }
        return bm;
    }
    public void cancelView(){
        setVisibility(GONE);
    }


}
