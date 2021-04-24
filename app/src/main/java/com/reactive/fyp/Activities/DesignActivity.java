package com.reactive.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reactive.fyp.Fragments.ShirtsFragment;
import com.reactive.fyp.Fragments.StickerFragment;
import com.reactive.fyp.Fragments.TextFragment;
import com.reactive.fyp.R;
import com.reactive.fyp.View.MaskView;
import com.reactive.fyp.View.MaskViewText;

public class DesignActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = DesignActivity.class.getSimpleName();
    public ImageView home_shirt,home_sticker;
    public ConstraintLayout header;
    RelativeLayout shirt,sticker,text;
    public MaskView maskView;
    public MaskViewText maskViewText;
    public boolean isFlipped=false;
    public boolean isRotation=false;
    public boolean isZoom=false;
    public TextView textView;
    float x = 0,y=0;
    float d = 0f;
    float newRot = 0f;
    float oldDist = 1f;
    private PointF mid = new PointF();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        maskView=findViewById(R.id.home_container);
        maskViewText=findViewById(R.id.text_mask_container);
        textView = findViewById(R.id.home_text);
        home_shirt=findViewById(R.id.home_shirt);
        home_sticker=findViewById(R.id.home_sticker);
        header=findViewById(R.id.header);
        shirt=findViewById(R.id.shirtWrapper);
        sticker=findViewById(R.id.stickerWrapper);
        text =findViewById(R.id.textwrapper);
        shirt.setOnClickListener(this);
        sticker.setOnClickListener(this);
        text.setOnClickListener(this);
        header.setOnClickListener(this);
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shirtContainer, new ShirtsFragment())
                    .commit();
        }

        Log.i(TAG,maskViewText.getChildCount()+" Children count");

        maskViewText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x=event.getX();
                        y=event.getY();
                        Log.i(TAG,"mastviewText clicks");
                        if (x>0 && x<100 && y>0 && y<100){
                            Log.i(TAG,"cancel clicks");
                            maskViewText.cancelView();
                        }else if (x>0 && x<100 && y<maskViewText.BOTTOM && y>maskViewText.BOTTOM-100){
                            isRotation=true;
                            d = rotation(event);
                            Log.i(TAG,"reload clicks");
                        }else if (x<maskViewText.RIGHT && x>maskViewText.RIGHT-100 && y>=0 && y<100){
                            if (isFlipped){
                                textView.setScaleY(1);
                                isFlipped=false;
                            }else {
                                textView.setScaleY(-1);
                                isFlipped=true;
                            }
                            Log.i(TAG,"flip clicks");
                        }else if (x<maskViewText.RIGHT && x>maskViewText.RIGHT-100 && y<maskViewText.BOTTOM && y>maskViewText.BOTTOM-100){
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                isZoom=true;
                                midPoint(mid, event);
                            }
                            Log.i(TAG,"zoom in out clicks");
                        }
                        x=v.getX() - event.getRawX();
                        y=v.getY() - event.getRawY();
                        if (!maskViewText.touched){
                            Log.i(TAG,maskViewText.touched+" masktext if");
                            maskViewText.touched=true;
                            maskViewText.invalidate();
                        }else {
                            Log.i(TAG,maskViewText.touched+" masktext");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isRotation){
                            isRotation=false;
                        }
                        if (isZoom){
                            isZoom=false;
                        }
                        Log.i(TAG,"Action up");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        Log.i(TAG,"Action pointer up");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isRotation){
                            newRot = rotation(event);
                            Log.i(TAG,"newRot "+newRot+"v.getRotation() "+v.getRotation()+" (newRot-d) "+(newRot-d));
                            v.setRotation((float) (v.getRotation() + (newRot - d)));
                        }else if (isZoom){
                            float newDist1 = spacing(event);
                            if (newDist1 > 10f) {
                                float scale = newDist1 / oldDist * v.getScaleX();
                                Log.i(TAG,"textview "+scale);
                                textView = (TextView)maskViewText.getChildAt(0);
                                v.setScaleX(scale);
                                v.setScaleY(scale);
                                Log.i(TAG,"v.getScaleX() "+v.getScaleX()+"v.getScaleY() "+v.getScaleY());
                            }
                        }else {
                            v.animate().x(event.getRawX()+x)
                                    .y(event.getRawY()+y)
                                    .setDuration(0)
                                    .start();
                        }

                        break;

                }
                return true;
            }
        });

        maskView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x=event.getX();
                        y=event.getY();
                        if (x>0 && x<100 && y>0 && y<100){
                            Log.i(TAG,"cancel clicks");
                            maskView.cancelView();
                        }else if (x>0 && x<100 && y<maskView.BOTTOM && y>maskView.BOTTOM-100){
                            isRotation=true;
                            d = rotation(event);
                            Log.i(TAG,"reload clicks");
                        }else if (x<maskView.RIGHT && x>maskView.RIGHT-100 && y>=0 && y<100){
                            if (isFlipped){
                                home_sticker.setScaleY(1);
                                isFlipped=false;
                            }else {
                                home_sticker.setScaleY(-1);
                                isFlipped=true;
                            }
                            Log.i(TAG,"flip clicks");
                        }else if (x<maskView.RIGHT && x>maskView.RIGHT-100 && y<maskView.BOTTOM && y>maskView.BOTTOM-100){
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                isZoom=true;
                                midPoint(mid, event);
                            }
                            Log.i(TAG,"zoom in out clicks");
                        }
                        x=v.getX() - event.getRawX();
                        y=v.getY() - event.getRawY();
                        if (!maskView.touched){
                            maskView.touched=true;
                            maskView.invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isRotation){
                            isRotation=false;
                        }
                        if (isZoom){
                            isZoom=false;
                        }
                        Log.i(TAG,"Action up");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        Log.i(TAG,"Action pointer up");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isRotation){
                            newRot = rotation(event);
                            Log.i(TAG,"newRot "+newRot+"v.getRotation() "+v.getRotation()+" (newRot-d) "+(newRot-d));
                            v.setRotation((float) (v.getRotation() + (newRot - d)));
                        }else if (isZoom){
                            float newDist1 = spacing(event);
                            if (newDist1 > 10f) {
                                float scale = newDist1 / oldDist * v.getScaleX();
                                v.setScaleX(scale);
                                v.setScaleY(scale);
                                Log.i(TAG,"v.getScaleX() "+v.getScaleX()+"v.getScaleY() "+v.getScaleY());
                            }
                        }else {
                            v.animate().x(event.getRawX()+x)
                                    .y(event.getRawY()+y)
                                    .setDuration(0)
                                    .start();
                        }

                        break;

                }
                return true;
            }
        });
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shirtWrapper:
                openWindow(new ShirtsFragment());
                break;
            case R.id.stickerWrapper:
                openWindow(new StickerFragment());
                break;
            case R.id.textwrapper:
                openWindow(new TextFragment());
                break;
            case R.id.header:
                maskView.clear();
                maskViewText.clear();
                break;
        }
    }

    void openWindow(Fragment fragment){
        FrameLayout frameLayout  = findViewById(R.id.shirtContainer);
        frameLayout.removeAllViews();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.shirtContainer,fragment)
                .commit();
    }

    private float rotation(MotionEvent event) {
        double delta_x = event.getX();
        double delta_y = event.getY();
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        point.set(x / 2, y / 2);
    }


}