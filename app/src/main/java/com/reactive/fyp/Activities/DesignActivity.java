package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.reactive.fyp.Dialog.BasicFragment;
import com.reactive.fyp.Dialog.InputText;
import com.reactive.fyp.Dialog.SizeFragment;
import com.reactive.fyp.Fragments.ShirtsFragment;
import com.reactive.fyp.Fragments.StickerFragment;
import com.reactive.fyp.Fragments.TextFragment;
import com.reactive.fyp.Interfaces.ImageListener;
import com.reactive.fyp.Interfaces.InputTextListener;
import com.reactive.fyp.Interfaces.SizeListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.View.ImageMaskView;
import com.reactive.fyp.View.MaskView;
import com.reactive.fyp.View.MaskViewText;
import com.reactive.fyp.model.ImageClass;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DesignActivity extends AppCompatActivity implements View.OnClickListener,ImageListener,
        SizeListener {

    private static final String TAG = DesignActivity.class.getSimpleName();
    public ImageView home_shirt,home_sticker,home_image,back;
    public ConstraintLayout header;
    RelativeLayout shirt,sticker,text,image,save;
    public MaskView maskView;
    public MaskViewText maskViewText;
    public ImageMaskView imageMaskView;
    public boolean isFlipped=false;
    public boolean isRotation=false;
    public boolean isZoom=false;
    public TextView textView,gallery;
    float x = 0,y=0;
    float d = 0f;
    float newRot = 0f;
    float oldDist = 1f;
    private PointF mid = new PointF();
    String size;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_IMAGES);
    private Uri filePath;
    private String downloadUrl;

    ProgressDialog progressDialog;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        maskView=findViewById(R.id.home_container);
        maskViewText=findViewById(R.id.text_mask_container);
        imageMaskView = findViewById(R.id.image_mask_container);
        back= findViewById(R.id.back);
        save = findViewById(R.id.save_wrapper);
        progressDialog = new ProgressDialog(this);
        textView = findViewById(R.id.home_text);
        image = findViewById(R.id.image_wrapper);
        home_image = findViewById(R.id.home_image);
        home_shirt=findViewById(R.id.home_shirt);
        home_sticker=findViewById(R.id.home_sticker);
        header=findViewById(R.id.header);
        shirt=findViewById(R.id.shirtWrapper);
        sticker=findViewById(R.id.stickerWrapper);
        text =findViewById(R.id.textwrapper);
        shirt.setOnClickListener(this);
        sticker.setOnClickListener(this);
        text.setOnClickListener(this);
        image.setOnClickListener(this);
        header.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_color));

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shirtContainer, new ShirtsFragment())
                    .commit();
        }

        Log.i(TAG,maskViewText.getChildCount()+" Children count");


        back.setOnClickListener(v -> {
            onBackPressed();
        });

        getImagePrice();

        imageMaskView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x=event.getX();
                        y=event.getY();
                        Log.i(TAG,"mastviewText clicks");
                        if (x>0 && x<100 && y>0 && y<100){
                            Log.i(TAG,"cancel clicks");
                            imageMaskView.cancelView();
                        }else if (x>0 && x<100 && y<imageMaskView.BOTTOM && y>imageMaskView.BOTTOM-100){
                            isRotation=true;
                            d = rotation(event);
                            Log.i(TAG,"reload clicks");
                        }else if (x<imageMaskView.RIGHT && x>imageMaskView.RIGHT-100 && y<imageMaskView.BOTTOM && y>imageMaskView.BOTTOM-100){
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                isZoom=true;
                                midPoint(mid, event);
                            }
                            Log.i(TAG,"zoom in out clicks");
                        }
                        x=v.getX() - event.getRawX();
                        y=v.getY() - event.getRawY();
                        if (!imageMaskView.touched){
                            Log.i(TAG,imageMaskView.touched+" masktext if");
                            imageMaskView.touched=true;
                            imageMaskView.invalidate();
                        }else {
                            Log.i(TAG,imageMaskView.touched+" masktext");
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
            case R.id.image_wrapper:
                showAlertDialog();
                break;
            case R.id.save_wrapper:
                maskView.clear();
                maskViewText.clear();
                imageMaskView.clear();
                openSizeDialog();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.header:
                maskView.clear();
                maskViewText.clear();
                imageMaskView.clear();
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

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        BasicFragment alertDialog = BasicFragment.newInstance();
        alertDialog.setListener(this);
        alertDialog.show(fm, "fragment_alert");
    }

    private void openSizeDialog(){
        FragmentManager fm = getSupportFragmentManager();
        SizeFragment alertDialog = new SizeFragment();
        alertDialog.setListener(this);
        alertDialog.show(fm, "fragment_size");
    }



    @Override
    public void ImageSelect(Uri uri) {
        imageMaskView.setVisibility(View.VISIBLE);
        home_image.setImageURI(uri);
    }

    public void getBitMapFromView(View view){

        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(header.getWidth(), header.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it


        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap

        FileOutputStream outputStream=null;
        File dir=new File(getExternalFilesDir(null).getAbsolutePath()
                + "/Art&Design/");
        Log.i(TAG,dir.toString());
        dir.mkdirs();

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss");

        String filename=format.format(new Date())+"Art&Design.jpeg";
        File Outfile=new File(dir,filename);
        try{
            outputStream=new FileOutputStream(Outfile);
            returnedBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            filePath = Uri.fromFile(new File(Outfile.getAbsolutePath()));
            Log.i(TAG,Outfile.getAbsolutePath());
            uploadImage();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    void uploadImage(){
        if(filePath != null)
        {
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage("Image is saving...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete());
                            downloadUrl = uriTask.getResult().toString();
                            addImage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.i(TAG,e.getMessage());
                            Toast.makeText(DesignActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    void addImage(){
        ImageClass imageClass = new ImageClass();
        imageClass.setOwnerId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        imageClass.setImage(downloadUrl);
        int total = Constants.FontPrice+Constants.stickerPrice+Constants.ImagePrice;
        imageClass.setPrice(total+"");
        imageClass.setActualPrice(total+"");
        imageClass.setDescription(Constants.DESCRIPTION);
        imageClass.setSize(size);

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(imageClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(DesignActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.i(TAG,e.getMessage());
                    }
                });

    }

    void getImagePrice(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    long val = (long) snapshot.child("ImagePrice").getValue();
                    Constants.ImagePrice = (int) val;
                }else {
                    Constants.ImagePrice = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG,error.getMessage());
            }
        });
    }

    @Override
    public void OnSize(String size) {
        this.size = size;
        getBitMapFromView(header);
    }
}