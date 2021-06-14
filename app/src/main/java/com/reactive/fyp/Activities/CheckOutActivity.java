package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.ActivityCheckOutBinding;
import com.reactive.fyp.model.CartClass;
import com.reactive.fyp.model.ImageClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {

    final String TAG = CheckOutActivity.class.getSimpleName();
    ActivityCheckOutBinding binding;
    CartClass cartClass;
    int total=0;
    SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_check_out);
        binding.setVisibility(true);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_color));

        if (getIntent().getExtras() != null){
            cartClass = (CartClass)getIntent().getExtras().getSerializable(Constants.PARAMS);
            Log.i(TAG,cartClass.toString());
        }
        binding.setData(cartClass);
        simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");
        binding.done.setOnClickListener(v -> {
            if (isValid()){
                binding.setVisibility(false);
                cartClass.setAddress(binding.address.getText().toString());
                cartClass.setTotal(total+"");
                cartClass.setStatus(false);
                cartClass.setTimestamp(simpleDateFormat.format(new Date()));
                addOrder();
            }
        });

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        getTotal();
    }


    boolean isValid(){
        cartClass.setDisplayError(true);
        if (!cartClass.getAddressError().isEmpty()){
            return false;
        }
        if (!cartClass.getPhoneError().isEmpty()){
            return false;
        }
        if (!cartClass.getPostalError().isEmpty()){
            return false;
        }
        cartClass.setDisplayError(false);
        return true;
    }

    void getTotal(){
        for (ImageClass imageClass:cartClass.getList()){
            total = total + Integer.parseInt(imageClass.getPrice());
        }
        binding.total.setText(total+"-/Rs");
    }

    void addOrder(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.Order);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(cartClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onBackPressed();
                        Toast.makeText(CheckOutActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CheckOutActivity.this, "try again", Toast.LENGTH_SHORT).show();
                        Log.i(TAG,e.getMessage());
                    }
                });
    }
}